package vn.io.vutiendat3601.chatsaas.dto.message;

import java.util.UUID;

public record ListMessageRequest(
    UUID channelId, Long pivotId, Integer prevLimit, Integer nextLimit) {}
