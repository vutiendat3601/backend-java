package vn.io.vutiendat3601.distributedlocking.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceResponse;
import vn.io.vutiendat3601.distributedlocking.service.UserService;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("v1/users")
public class UserController {
  private final UserService userService;

  @GetMapping("{id}/balance")
  public ResponseEntity<GetUserBalanceResponse> getUserBalance(@PathVariable Long id) {
    var getUserBalanceResponse = userService.getUserBalance(new GetUserBalanceRequest(id));
    return ResponseEntity.ok(getUserBalanceResponse);
  }
}
