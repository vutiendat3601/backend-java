package vn.io.vutiendat3601.instamini.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.instamini.dto.NotificationDto;
import vn.io.vutiendat3601.instamini.entity.Notification;

@RequiredArgsConstructor
@Component
public class NotificationMapper {
  private final ProfileMapper profileMapper;
  private final PostMapper postMapper;

  public NotificationDto mapToNotificationDto(Notification notification) {
    return new NotificationDto(
        notification.getId(),
        profileMapper.mapToProfileDto(notification.getFromProfile()),
        profileMapper.mapToProfileDto(notification.getToProfile()),
        notification.getType(),
        notification.getCreatedAt(),
        postMapper.mapToPostDto(notification.getPost()));
  }
}
