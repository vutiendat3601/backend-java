package vn.io.vutiendat3601.instamini.dto;

import java.time.Instant;
import vn.io.vutiendat3601.instamini.constant.NotificationType;

public record NotificationDto(
    Long id,
    ProfileDto fromProfile,
    ProfileDto toProfile,
    NotificationType type,
    Instant createdAt,
    PostDto post) {}
