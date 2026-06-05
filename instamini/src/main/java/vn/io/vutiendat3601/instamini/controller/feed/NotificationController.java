package vn.io.vutiendat3601.instamini.controller.feed;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListNotificationResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.service.NotificationService;

@Tag(name = "Notification")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/notifications")
@RestController
public class NotificationController {
  private final NotificationService notificationService;

  @GetMapping("me")
  public ResponseEntity<ListNotificationResponse> listNotifications(
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    var listNotificationResp = notificationService.listNotifications(userPrincipal);
    return ResponseEntity.ok(listNotificationResp);
  }
}
