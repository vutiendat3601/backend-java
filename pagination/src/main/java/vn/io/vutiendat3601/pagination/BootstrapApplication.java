package vn.io.vutiendat3601.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootstrapApplication {
  private static final int CASE_NUMBER = 0;

  public static void main(String[] args) {
    final ApplicationContext ctx = SpringApplication.run(BootstrapApplication.class, args);
    final UseCase useCase = ctx.getBean(UseCase.class);
    useCase.tryCase(CASE_NUMBER);
  }
}
