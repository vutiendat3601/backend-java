package vn.io.vutiendat3601.instamini.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListNotificationResponse;
import vn.io.vutiendat3601.instamini.mapper.NotificationMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.NotificationRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.NotificationService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@RequiredArgsConstructor
@Service
public class NotificationServiceV1 implements NotificationService {
  private final NotificationMapper notificationMapper;
  private final NotificationRepository notificationRepository;
  private final ProfileRepository profileRepository;
  private final ProfileService profileService;

  @Override
  public ListNotificationResponse listNotifications(UserPrincipal userPrincipal) {
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    var userPrincipalProfile = profileRepository.getReferenceById(userPrincipalProfileDto.id());
    var notifications = notificationRepository.findByToProfile(userPrincipalProfile);
    var notificationDtos =
        notifications.stream().map(notificationMapper::mapToNotificationDto).toList();
    return new ListNotificationResponse(notificationDtos);
  }
}
