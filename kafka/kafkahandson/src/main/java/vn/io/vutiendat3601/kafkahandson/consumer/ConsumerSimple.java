package vn.io.vutiendat3601.kafkahandson.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

@Slf4j
public class ConsumerSimple {
  public static void main(String[] args) throws InterruptedException {
    var mainThread = Thread.currentThread();

    log.info("### Simple Kafka Consumer ###\n\n\n");
    var topic = "kafka_hands_on";

    // # Create Producer Properties
    var props = new Properties();

    // Server
    props.setProperty("bootstrap.servers", "localhost:9092");

    // Group ID
    props.setProperty("group.id", "consumer-simple-1");

    // Deserializer
    props.setProperty("key.deserializer", StringDeserializer.class.getName());
    props.setProperty("value.deserializer", StringDeserializer.class.getName());

    // auto offset reset: (none|earliest|latest). 'none' throws exception
    // props.setProperty("auto.offset.reset", "none");

    // # Create Consumer
    var consumer = new KafkaConsumer<String, String>(props);

    // Graceful shutdown
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                log.info("Detected shutdown, let's exit by calling consumer.wakeup() ...");
                consumer.wakeup();
                try {
                  mainThread.join();
                } catch (InterruptedException e) {
                  log.error("Error when shutdown", e);
                }
              }
            });

    // # Subscribe for topic
    consumer.subscribe(Arrays.asList(topic));

    // # Polling for data
    try {
      while (true) {
        log.info("Polling data");
        var consumerRecords = consumer.poll(Duration.ofSeconds(1));
        int numberOfRecords = consumerRecords.count();
        log.info("### Received numbesr of records: {} ###", numberOfRecords);
        for (var record : consumerRecords) {
          log.info("Key: {}, value: {}", record.key(), record.value());
          log.info("Partition: {}, Offset: {}", record.partition(), record.offset());
        }
        TimeUnit.SECONDS.sleep(1L);
      }
    } catch (WakeupException e) {
      log.info(
          "Consumer is starting to shutdown: memberId={}", consumer.groupMetadata().memberId());
    } finally {
      consumer.close();
      log.info("Consumer is graceful shutdown");
    }
  }
}
