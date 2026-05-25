package vn.io.vutiendat3601.distributedlocking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceResponse;
import vn.io.vutiendat3601.distributedlocking.entity.User;
import vn.io.vutiendat3601.distributedlocking.exception.UserNotFoundException;
import vn.io.vutiendat3601.distributedlocking.repository.UserRepository;
import vn.io.vutiendat3601.distributedlocking.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceV1 implements UserService {
  private final UserRepository userRepository;

  @Override
  public GetUserBalanceResponse getUserBalance(GetUserBalanceRequest getUserBalanceReq) {
    var userId = getUserBalanceReq.userId();
    final User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () -> new UserNotFoundException("User with id '%d' not found.".formatted(userId)));
    return new GetUserBalanceResponse(user.getBalance());
  }
}
