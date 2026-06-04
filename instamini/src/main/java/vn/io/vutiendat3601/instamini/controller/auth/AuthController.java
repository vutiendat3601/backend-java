package vn.io.vutiendat3601.instamini.controller.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.response.AuthenticationResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

@Tag(name = "Auth")
@Slf4j
@RequestMapping("v1/auth")
@RestController
public class AuthController {
  @GetMapping("inspect")
  public ResponseEntity<AuthenticationResponse<? extends OAuth2User>> inspect(
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    log.info("Authentication: id=%s", userPrincipal.getId() + "");
    return ResponseEntity.ok(new AuthenticationResponse<>(userPrincipal));
  }
}
