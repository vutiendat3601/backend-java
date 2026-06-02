package vn.io.vutiendat3601.chatsaas.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.user.CreateUserRequest;
import vn.io.vutiendat3601.chatsaas.dto.user.UserDto;
import vn.io.vutiendat3601.chatsaas.entity.User;
import vn.io.vutiendat3601.chatsaas.exception.ClientUserIdExistedException;
import vn.io.vutiendat3601.chatsaas.exception.UserNotFoundException;
import vn.io.vutiendat3601.chatsaas.mapper.AppMapper;
import vn.io.vutiendat3601.chatsaas.mapper.UserMapper;
import vn.io.vutiendat3601.chatsaas.repository.UserRepository;
import vn.io.vutiendat3601.chatsaas.service.UserService;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceV1 implements UserService {
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final AppMapper appMapper;

  @Override
  public UserDto getUserByClientUserId(AppDto authenticatedAppDto, String clientUserId) {
    var user =
        userRepository
            .findByAppIdAndClientUserId(authenticatedAppDto.id(), clientUserId)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        "User not found: appId=%s, clientUserId=%s"
                            .formatted(authenticatedAppDto.id().toString(), clientUserId)));
    return userMapper.mapToUserDto(user);
  }

  @Override
  public UserDto createUser(AppDto authenticatedAppDto, CreateUserRequest createUserReq) {
    log.info("Create user app={} request={}", authenticatedAppDto, createUserReq);
    if (userRepository
        .findByAppIdAndClientUserId(authenticatedAppDto.id(), createUserReq.clientUserId())
        .isPresent()) {
      throw new ClientUserIdExistedException("");
    }
    log.info("not exist user, create now {}", authenticatedAppDto);
    var app = appMapper.mapToApp(authenticatedAppDto);
    var user =
        User.builder()
            .app(app)
            .clientUserId(createUserReq.clientUserId())
            .name(createUserReq.name())
            .profileImgUrl(createUserReq.profileImgUrl())
            .build();
    user = userRepository.save(user);
    return userMapper.mapToUserDto(user);
  }
}
