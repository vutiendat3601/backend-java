package vn.io.vutiendat3601.instamini.service;

import vn.io.vutiendat3601.instamini.dto.response.feed.ListNotificationResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface NotificationService {
  ListNotificationResponse listNotifications(UserPrincipal userPrincipal);
}
