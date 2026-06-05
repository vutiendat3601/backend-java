package vn.io.vutiendat3601.instamini.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.io.vutiendat3601.instamini.constant.MessageQueueConstant;
import vn.io.vutiendat3601.instamini.constant.NotificationType;
import vn.io.vutiendat3601.instamini.dto.event.LikePostEvent;
import vn.io.vutiendat3601.instamini.dto.request.feed.GetPostRequest;
import vn.io.vutiendat3601.instamini.entity.Notification;
import vn.io.vutiendat3601.instamini.repository.NotificationRepository;
import vn.io.vutiendat3601.instamini.repository.PostRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.feed.PostService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@RequiredArgsConstructor
@Slf4j
@Component
@RabbitListener(queues = MessageQueueConstant.QUEUE_LIKE_POST)
public class EventConsumer {
  private final ObjectMapper objectMapper;

  private final ProfileRepository profileRepository;

  private final PostRepository postRepository;

  private final ProfileService profileService;

  private final PostService postService;

  private final NotificationRepository notificationRepository;

  @Transactional
  @RabbitHandler
  public void processLikePostEvent(String serializedLikePostEvent)
      throws JsonMappingException, JsonProcessingException {
    log.info("Received LikePostEvent: " + serializedLikePostEvent);

    var likePostEvent = objectMapper.readValue(serializedLikePostEvent, LikePostEvent.class);
    log.info(
        "LikePostEvent: postId={}, profileId={}",
        likePostEvent.postId(),
        likePostEvent.likerProfileId());

    var likerProfileDto = profileService.getProfile(likePostEvent.likerProfileId()).profile();
    var likerProfile = profileRepository.getReferenceById(likerProfileDto.id());
    var postDto = postService.getPost(new GetPostRequest(likePostEvent.postId())).post();
    var post = postRepository.getReferenceById(postDto.id());
    var createdByProfile = profileRepository.getReferenceById(postDto.createdBy().id());
    var notification =
        Notification.builder()
            .fromProfile(likerProfile)
            .toProfile(createdByProfile)
            .type(NotificationType.LIKE_YOUR_POST)
            .post(post)
            .createdAt(Instant.now())
            .build();
    notificationRepository.save(notification);
    log.info("Processed LikePostEvent: " + serializedLikePostEvent);
  }
}
