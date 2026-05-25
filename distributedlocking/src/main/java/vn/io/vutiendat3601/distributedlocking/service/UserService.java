package vn.io.vutiendat3601.distributedlocking.service;

import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserBalanceResponse;

public interface UserService {
  GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request);
}
