package vn.io.vutiendat3601.chatsaas.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(
    @NotEmpty String name, @NotEmpty String clientUserId, @NotEmpty String profileImgUrl) {}
