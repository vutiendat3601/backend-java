package vn.io.vutiendat3601.instamini.configuration;

import static vn.io.vutiendat3601.instamini.constant.MessageQueueConstant.QUEUE_LIKE_POST;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfiguration {
  @Bean
  Queue queueLikePost() {
    return QueueBuilder.durable(QUEUE_LIKE_POST).build();
  }
}
