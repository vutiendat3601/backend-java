package vn.io.vutiendat3601.relationaldatabaselocking.service.impl;

import jakarta.transaction.Transactional;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Booking;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Room;
import vn.io.vutiendat3601.relationaldatabaselocking.exception.ConflictRoomBookingException;
import vn.io.vutiendat3601.relationaldatabaselocking.exception.RoomNotFoundException;
import vn.io.vutiendat3601.relationaldatabaselocking.repository.BookingRepository;
import vn.io.vutiendat3601.relationaldatabaselocking.repository.RoomRepository;
import vn.io.vutiendat3601.relationaldatabaselocking.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceV1 implements UserService {
  private final int SLEEP_SECONDS = 3;
  private final RoomRepository roomRepository;
  private final BookingRepository bookingRepository;

  @Transactional
  @Override
  public Booking bookRoomUsingPessimisticLocking(Long userId, Long roomId) {
    log.info("Start booking room, userId = {}, roomId = {}", userId, roomId);
    final Room room =
        roomRepository
            .findByIdAndAvailable(
                roomId,
                true) // Lock here: findByIdAndAvailable(roomId, true), wait until the lock released
            .orElseThrow(
                () -> {
                  log.info("Room not found, roomId = {}", roomId);
                  return new RoomNotFoundException();
                });
    log.info("Locked row for update, roomId = {}", roomId);
    final Booking booking = Booking.builder().roomId(room.getId()).userId(userId).build();
    bookingRepository.save(booking);
    try {
      Thread.sleep(Duration.ofSeconds(SLEEP_SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int numOfUpdatedRows = roomRepository.updateRoomAsUnavailableUsingPessimisticLocking(roomId);
    if (numOfUpdatedRows == 0) {
      log.info("Booked room unsuccessfully, the room was changed, id = {}", room.getId());
      throw new ConflictRoomBookingException();
    }
    log.info("Booked room successfully, userId = {}, id = {}", userId, room.getId());
    return booking;
  }

  @Transactional
  @Override
  public Booking bookRoomUsingOptimisticLocking(Long userId, Long roomId) {
    log.info("Start booking room, userId = {}, id = {}", userId, roomId);
    final Room room =
        roomRepository
            .findOneByIdAndAvailable(roomId, true)
            .orElseThrow(
                () -> {
                  log.info("Room not found, id = {}", roomId);
                  return new RoomNotFoundException();
                });
    final Booking booking = Booking.builder().roomId(room.getId()).userId(userId).build();
    bookingRepository.save(booking);
    try {
      Thread.sleep(Duration.ofSeconds(SLEEP_SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int numOfUpdatedRows =
        roomRepository.updateRoomAsUnavailableUsingOptimisticLocking(
            roomId, room.getVersion()); // Lock here
    if (numOfUpdatedRows == 0) {
      log.info(
"""
Booked room unsuccessfully, the room was changed, id = {}. The change is being roll back
""",
          room.getId());
      throw new ConflictRoomBookingException();
    }
    log.info("Booked room successfully, userId = {}, id = {}", userId, room.getId());
    return booking;
  }
}
