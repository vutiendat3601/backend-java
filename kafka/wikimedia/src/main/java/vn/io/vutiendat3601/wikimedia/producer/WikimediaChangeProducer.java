package vn.io.vutiendat3601.wikimedia.producer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.record.internal.CompressionType;
import org.apache.kafka.common.serialization.StringSerializer;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;

import vn.io.vutiendat3601.wikimedia.handler.WikimediaChangeHandler;

public class WikimediaChangeProducer {
  private static final Properties KAFKA_PROPS = new Properties();
  private static final String TOPIC = "wikimedia.recentchanges";
  private static final String USER_AGENT =
      "Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like"
          + " Gecko) Chrome/148.0.0.0 Mobile Safari/537.36";
  private static final String URL = "https://stream.wikimedia.org/v2/stream/recentchange";

  private static KafkaProducer<String, String> kafkaProducer;

  static {
    KAFKA_PROPS.setProperty("bootstrap.servers", "127.0.0.1:9092");
    KAFKA_PROPS.setProperty("key.serializer", StringSerializer.class.getName());
    KAFKA_PROPS.setProperty("value.serializer", StringSerializer.class.getName());

    // Safe producer (idempotent producer) configuration.
    KAFKA_PROPS.setProperty("enable.idempotence", "true");
    KAFKA_PROPS.setProperty("acks", "-1");
    KAFKA_PROPS.setProperty("retries", Integer.MAX_VALUE + "");

    // High throughput configuration
    KAFKA_PROPS.setProperty("linger.ms", "20"); // ms
    KAFKA_PROPS.setProperty("batch.size", (1024 * 32) + ""); // bytes
    KAFKA_PROPS.setProperty("compression.type", CompressionType.SNAPPY.toString());

    kafkaProducer = new KafkaProducer<>(KAFKA_PROPS);
  }

  public static void main(String[] args) throws InterruptedException {
    var eventHandler = new WikimediaChangeHandler(TOPIC, kafkaProducer);
    var eventSourceBuilder =
        new EventSource.Builder(
            ConnectStrategy.http(URI.create(URL)).header("User-Agent", USER_AGENT));
    var backgroundEventSource =
        new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder).build();

    backgroundEventSource.start();

    TimeUnit.SECONDS.sleep(1);
  }
}
