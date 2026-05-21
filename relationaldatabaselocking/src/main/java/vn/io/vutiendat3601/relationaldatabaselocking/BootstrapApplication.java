package vn.io.vutiendat3601.relationaldatabaselocking;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Room;
import vn.io.vutiendat3601.relationaldatabaselocking.repository.RoomRepository;
import vn.io.vutiendat3601.relationaldatabaselocking.service.UserService;

@Slf4j
@SpringBootApplication
public class BootstrapApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootstrapApplication.class, args);
  }

  // @EventListener
  void bookRoomUsingPessimisticLocking(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var roomRepository = ctx.getBean(RoomRepository.class);
    var userService = ctx.getBean(UserService.class);
    var room1 = Room.builder().name("A001").available(true).build();
    roomRepository.save(room1);
    var threads = new ArrayList<Thread>();
    for (int i = 0; i < 5; i++) {
      var thread = new Thread(() -> userService.bookRoomUsingPessimisticLocking(1L, 1L));
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
  }

  @EventListener
  void bookRoomUsingOptimisticLocking(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var roomRepository = ctx.getBean(RoomRepository.class);
    var userService = ctx.getBean(UserService.class);
    var room1 = Room.builder().name("A001").available(true).build();
    roomRepository.save(room1);
    var threads = new ArrayList<Thread>();
    for (int i = 0; i < 5; i++) {
      var thread = new Thread(() -> userService.bookRoomUsingOptimisticLocking(1L, 1L));
      threads.add(thread);
      thread.start();
    }
    for (var thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
