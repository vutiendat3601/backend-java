package vn.io.vutiendat3601.instamini.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.instamini.constant.MessageQueueConstant;
import vn.io.vutiendat3601.instamini.dto.event.AfterCreatePostEvent;
import vn.io.vutiendat3601.instamini.dto.event.LikePostEvent;

@RequiredArgsConstructor
@Slf4j
@Component
public class EventProducer {
  private final RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper;

  public void sendAfterCreatePostEvent(AfterCreatePostEvent afterCreatePostEvent) {
    try {
      var message = objectMapper.writeValueAsString(afterCreatePostEvent);
      rabbitTemplate.convertAndSend(MessageQueueConstant.QUEUE_AFTER_CREATE_POST, message);
      log.info("Sent AfterCreatePostEvent: postId={}", afterCreatePostEvent.postId());
    } catch (JsonProcessingException | AmqpException e) {
      log.error("Cannot send AfterCreatePostEvent: postId={}", afterCreatePostEvent.postId());
    }
  }

  public void sendLikePostEvent(LikePostEvent likePostEvent) {
    try {
      rabbitTemplate.convertAndSend(
          MessageQueueConstant.QUEUE_LIKE_POST, objectMapper.writeValueAsString(likePostEvent));
      log.info(
          "Sent LikePostEvent: likerProfileId={}, postId={}",
          likePostEvent.likerProfileId(),
          likePostEvent.postId());
    } catch (JsonProcessingException | AmqpException e) {
      log.error(
          "Cannot send LikePostEvent: likerProfileId={}, postId={}",
          likePostEvent.likerProfileId(),
          likePostEvent.postId());
    }
  }
}
