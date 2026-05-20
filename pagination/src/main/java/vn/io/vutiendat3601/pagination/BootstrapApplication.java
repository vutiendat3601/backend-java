package vn.io.vutiendat3601.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BootstrapApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootstrapApplication.class, args);
  }

  @EventListener
  void onStartup(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var seeder = ctx.getBean(Seeder.class);
    seeder.setUp();
  }

  @EventListener
  void onShutdown(ContextClosedEvent event) {
    var ctx = event.getApplicationContext();
    var seeder = ctx.getBean(Seeder.class);
    seeder.tearDown();
  }
}
