package vn.io.vutiendat3601.chatsaas.dto.channel;

import java.time.Instant;
import java.util.UUID;

public record ChannelDto(
    UUID id, String name, UUID appId, String clientReferenceId, Instant createdAt) {}
