package vn.io.vutiendat3601.chatsaas.service;

import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.user.CreateUserRequest;
import vn.io.vutiendat3601.chatsaas.dto.user.UserDto;

public interface UserService {
  UserDto getUserByClientUserId(AppDto authenticatedAppDto, String clientUserId);

  UserDto createUser(AppDto authenticatedAppDto, CreateUserRequest createUserReq);
}
