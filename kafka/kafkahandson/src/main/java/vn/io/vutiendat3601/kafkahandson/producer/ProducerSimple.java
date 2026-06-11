package vn.io.vutiendat3601.kafkahandson.producer;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class ProducerSimple {
  public static void main(String[] args) {
    log.info("### Simple Kafka Producer ###\n\n\n");
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
    for (int i = 0; i < 1_000; i++) {
      var producerRecord =
          new ProducerRecord<String, String>(topic, "Hello, world! - " + i);
      producer.send(producerRecord);
    }

    // # Flush and close Producer
    producer.flush(); // Tell the producer to send all data and block until done.
    producer.close();
  }
}
