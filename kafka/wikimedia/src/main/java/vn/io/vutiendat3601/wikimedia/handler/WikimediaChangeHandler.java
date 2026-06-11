package vn.io.vutiendat3601.wikimedia.handler;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements BackgroundEventHandler {
  private static final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class);

  private final String topic;

  private final KafkaProducer<String, String> kafkaProducer;

  public WikimediaChangeHandler(String topic, KafkaProducer<String, String> kafkaProducer) {
    this.topic = topic;
    this.kafkaProducer = kafkaProducer;
  }

  @Override
  public void onOpen() throws Exception {}

  @Override
  public void onClosed() throws Exception {
    kafkaProducer.close();
    log.info("The Kafka stream is closed.");
  }

  @Override
  public void onMessage(String event, MessageEvent messageEvent) throws Exception {
    log.info(messageEvent.getData());
    // Asynchronous
    kafkaProducer.send(new ProducerRecord<String, String>(topic, messageEvent.getData()));
  }

  @Override
  public void onComment(String comment) throws Exception {}

  @Override
  public void onError(Throwable t) {
    log.error("Error in stream reading: ", t);
  }
}
