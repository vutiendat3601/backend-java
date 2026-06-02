package vn.io.vutiendat3601.chatsaas.dto.channel;

import jakarta.validation.constraints.NotEmpty;

public record CreateChannelRequest(@NotEmpty String name, @NotEmpty String clientReferenceId) {}
