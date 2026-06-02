package vn.io.vutiendat3601.chatsaas.controller;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.AUTHENTICATED_APP_REQUEST_ATTRIBUTE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.user.CreateUserRequest;
import vn.io.vutiendat3601.chatsaas.dto.user.CreateUserResponse;
import vn.io.vutiendat3601.chatsaas.dto.user.GetUserResponse;
import vn.io.vutiendat3601.chatsaas.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
@Timed(histogram = true)
public class UserController extends AbstractController {
  private final UserService userService;

  @GetMapping("{clientUserId}/by-client-user-id")
  public ResponseEntity<GetUserResponse> getUser(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @PathVariable String clientUserId) {
    var userDto = userService.getUserByClientUserId(appDto, clientUserId);
    return ResponseEntity.ok(new GetUserResponse(userDto));
  }

  @PostMapping
  public ResponseEntity<CreateUserResponse> createUser(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @Valid @RequestBody CreateUserRequest request) {
    var userDto = userService.createUser(appDto, request);
    return ResponseEntity.ok(new CreateUserResponse(userDto));
  }
}
