package vn.io.vutiendat3601.instamini.dto.response.feed;

import java.util.List;
import vn.io.vutiendat3601.instamini.dto.NotificationDto;

public record ListNotificationResponse(List<NotificationDto> notifications) {}
