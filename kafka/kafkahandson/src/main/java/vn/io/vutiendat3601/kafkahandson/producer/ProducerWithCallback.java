package vn.io.vutiendat3601.kafkahandson.producer;

import java.time.Instant;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/*
 * Confirm the partition and offset the message was sent using Callback
 * StickyPartitioner
 */

@Slf4j
public class ProducerWithCallback {
  public static void main(String[] args) {
    log.info("### Kafka Producer with Callback ###\n\n\n");
    var topic = "kafka_hands_on";

    // # Create Producer Properties
    var props = new Properties();

    // Server
    props.setProperty("bootstrap.servers", "localhost:9092");

    // Serializer
    props.setProperty("key.serializer", StringSerializer.class.getName());
    props.setProperty("value.serializer", StringSerializer.class.getName());
    props.setProperty("batch.size", "1024"); // bytes
    // props.setProperty("auto.create.topics.enable", "true"); // DEV

    // # Create Producer
    var producer = new KafkaProducer<String, String>(props);

    // # Send data
    var callback =
        (Callback)
            (metadata, e) -> {
              if (Objects.nonNull(e)) {
                log.error("Error while producing: ", e);
                return;
              }
              log.info("Topic: {}", metadata.topic());
              log.info("Partition: {}", metadata.partition());
              log.info("Offset: {}", metadata.offset());
              log.info("Timestamp: {}", Instant.ofEpochMilli(metadata.timestamp()));
            };
    for (int i = 0; i < 1_000; i++) {
      var producerRecord =
          new ProducerRecord<String, String>(topic, "Hello, world! - " + i);
      producer.send(producerRecord, callback);
    }

    // # Flush and close Producer
    producer.flush(); // Tell the producer to send all data and block until done.
    producer.close();
  }
}
