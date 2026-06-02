package vn.io.vutiendat3601.chatsaas.dto.message;

import jakarta.validation.constraints.NotEmpty;

public record SendMessageRequest(
    @NotEmpty String clientUserId, @NotEmpty String content, @NotEmpty String imgUrl) {}
