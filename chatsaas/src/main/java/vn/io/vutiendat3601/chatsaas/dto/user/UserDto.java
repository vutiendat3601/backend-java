package vn.io.vutiendat3601.chatsaas.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
    UUID id,
    UUID appId,
    String clientUserId,
    String name,
    String profileImgUrl,
    Instant createdAt) {}
