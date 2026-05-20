package vn.io.vutiendat3601.imdb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.imdb.dto.LoginRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationResponse;
import vn.io.vutiendat3601.imdb.model.User;
import vn.io.vutiendat3601.imdb.service.UserService;

@Slf4j
@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController extends AbstractController {
  private final UserService userService;

  @GetMapping("inspect")
  public ResponseEntity<User> inspect(HttpServletRequest req) {
    var user = getUserFromSession(req);
    log.info("Inspect user: {}", user);
    if (Objects.isNull(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginReq, HttpServletRequest req) {
    var user = userService.login(loginReq);
    req.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
    return ResponseEntity.ok().build();
  }

  @PostMapping("register")
  public ResponseEntity<RegistrationResponse> register(
      @Valid @RequestBody RegistrationRequest registrationReq, HttpServletRequest request) {
    var resp = userService.createUser(registrationReq);
    if (Objects.isNull(resp)) {
      return ResponseEntity.unprocessableContent().build();
    }
    return ResponseEntity.ok(resp);
  }
}
