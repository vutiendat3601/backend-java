package vn.io.vutiendat3601.distributedlocking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.distributedlocking.entity.User;
import vn.io.vutiendat3601.distributedlocking.repository.UserRepository;

@SpringBootApplication
public class DistributedlockingApplication {
  public static void main(String[] args) {
    SpringApplication.run(DistributedlockingApplication.class, args);
  }

  @EventListener
  void startUp(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var userRepository = ctx.getBean(UserRepository.class);
    var user = User.builder().balance(1_000_000L).build();
    userRepository.save(user);
  }
}
