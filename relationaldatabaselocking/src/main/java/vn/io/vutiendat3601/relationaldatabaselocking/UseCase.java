package vn.io.vutiendat3601.relationaldatabaselocking;

import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Room;
import vn.io.vutiendat3601.relationaldatabaselocking.repository.RoomRepository;
import vn.io.vutiendat3601.relationaldatabaselocking.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UseCase {
  private final UserService userService;
  private final RoomRepository roomRepository;

  final Room room1 = Room.builder().name("A001").available(true).build();

  private void setUp() {
    roomRepository.save(room1);
  }

  private void tearDown() {
    roomRepository.delete(room1);
  }

  public void tryCase(int caseNumber) {
    setUp();
    final List<Thread> threads = new LinkedList<>();
    // userService.bookRoomUsingPessimisticLocking(1L, 1L);
    Runnable BOOK_ROOM_USING_PESSIMISTIC =
        () -> {
          try {
            userService.bookRoomUsingPessimisticLocking(1L, 1L);
          } catch (Exception e) {
            log.info("Exceptionx: ", e.getClass());
          }
        };
    Runnable BOOK_ROOM_USING_OPTIMISTIC =
        () -> {
          try {
            userService.bookRoomUsingOptimisticLocking(1L, 1L);
          } catch (Exception e) {
            log.info("Exception: ", e.getClass());
          }
        };
    Runnable bookRoomRunnable = BOOK_ROOM_USING_PESSIMISTIC;

    switch (caseNumber) {
      case 0:
        bookRoomRunnable = BOOK_ROOM_USING_PESSIMISTIC;
        break;
      case 1:
        bookRoomRunnable = BOOK_ROOM_USING_OPTIMISTIC;
        break;
      default:
        break;
    }
    for (int i = 0; i < 5; i++) {
      final Thread thread = new Thread(bookRoomRunnable);
      threads.add(thread);
      thread.start();
    }
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    tearDown();
  }
}
