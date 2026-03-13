package vn.io.vutiendat3601.relationaldatabaselocking.service;

import vn.io.vutiendat3601.relationaldatabaselocking.entity.Booking;

public interface UserService {
  Booking bookRoomUsingPessimisticLocking(Long userId, Long roomId);

  Booking bookRoomUsingOptimisticLocking(Long userId, Long roomId);
}
