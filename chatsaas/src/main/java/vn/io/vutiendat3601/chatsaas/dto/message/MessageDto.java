package vn.io.vutiendat3601.chatsaas.dto.message;

import java.time.Instant;
import java.util.UUID;

public record MessageDto(
    Long id,
    String messageOwnerClientUserId,
    String messageOwner,
    UUID channelId,
    String content,
    String imgUrl,
    Boolean isDeleted,
    Instant createdAt,
    Instant updatedAt) {}
