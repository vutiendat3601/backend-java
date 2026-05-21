package vn.io.vutiendat3601.relationaldatabaselocking.service.impl;

import jakarta.transaction.Transactional;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Booking;
import vn.io.vutiendat3601.relationaldatabaselocking.exception.ConflictResourceException;
import vn.io.vutiendat3601.relationaldatabaselocking.exception.ResourceNotFoundException;
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
    log.info("Start booking room: userId={},roomId={}. Using Pessimistic Locking.", userId, roomId);
    var room =
        roomRepository
            .findByIdAndAvailable(
                roomId,
                true) // Lock here: findByIdAndAvailable(roomId, true), wait until the lock released
            .orElseThrow(
                () -> {
                  log.info("Room not found: roomId={}", roomId);
                  return new ResourceNotFoundException(
                      "Room not found, roomId=%d".formatted(roomId));
                });
    log.info("Locked row for update: roomId={}", roomId);
    var booking = Booking.builder().roomId(room.getId()).userId(userId).build();
    bookingRepository.save(booking);
    try {
      Thread.sleep(Duration.ofSeconds(SLEEP_SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    var numOfUpdatedRows = roomRepository.updateRoomAsUnavailableUsingPessimisticLocking(roomId);
    if (numOfUpdatedRows == 0) {
      log.info("Booked room unsuccessfully, the room was changed: roomId={}", room.getId());
      throw new ConflictResourceException(
          "Booked room unsuccessfully, the room was changed: roomId=%d".formatted(room.getId()));
    }
    log.info("Booked room successfully: userId={}, roomId={}", userId, room.getId());
    return booking;
  }

  @Transactional
  @Override
  public Booking bookRoomUsingOptimisticLocking(Long userId, Long roomId) {
    log.info("Start booking room: userId={},roomId={}. Using Optimistic Locking.", userId, roomId);
    var room =
        roomRepository
            .findOneByIdAndAvailable(roomId, true)
            .orElseThrow(
                () -> {
                  log.info("Room not found: roomId={}", roomId);
                  return new ResourceNotFoundException("Room not found, roomId=%d".formatted(roomId));
                });
    var booking = Booking.builder().roomId(room.getId()).userId(userId).build();
    bookingRepository.save(booking);
    try {
      Thread.sleep(Duration.ofSeconds(SLEEP_SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    var numOfUpdatedRows =
        roomRepository.updateRoomAsUnavailableUsingOptimisticLocking(
            roomId, room.getVersion()); // Lock here
    if (numOfUpdatedRows == 0) {
      log.info(
          "Booked room unsuccessfully, the room was changed: roomId={}. The changes are being roll back.",
          room.getId());
      throw new ConflictResourceException(
          "Booked room unsuccessfully, the room was changed: roomId=%d".formatted(roomId));
    }
    log.info("Booked room successfully: userId={}, roomId={}", userId, room.getId());
    return booking;
  }
}
