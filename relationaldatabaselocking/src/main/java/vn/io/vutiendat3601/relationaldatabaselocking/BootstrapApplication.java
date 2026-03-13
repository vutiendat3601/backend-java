package vn.io.vutiendat3601.relationaldatabaselocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class BootstrapApplication {
  private static final int CASE_NUMBER = 1;

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(BootstrapApplication.class, args);
    final UseCase useCase = ctx.getBean(UseCase.class);
    useCase.tryCase(CASE_NUMBER);
    log.info("COMPLETE");
  }
}
