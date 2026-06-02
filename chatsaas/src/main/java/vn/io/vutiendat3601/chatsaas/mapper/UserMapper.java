package vn.io.vutiendat3601.chatsaas.mapper;

import org.springframework.stereotype.Component;

import vn.io.vutiendat3601.chatsaas.dto.user.UserDto;
import vn.io.vutiendat3601.chatsaas.entity.User;

@Component
public class UserMapper {
  public UserDto mapToUserDto(User user) {
    return new UserDto(
        user.getId(),
        user.getApp().getId(),
        user.getClientUserId(),
        user.getName(),
        user.getProfileImgUrl(),
        user.getCreatedAt());
  }
}
