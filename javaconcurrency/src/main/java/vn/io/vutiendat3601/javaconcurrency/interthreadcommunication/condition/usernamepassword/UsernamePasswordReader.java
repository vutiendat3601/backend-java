package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.condition.usernamepassword;

import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UsernamePasswordReader extends Thread {
  private final UsernamePasswordToken usernamePasswordToken;
  private final Scanner scanner;

  @Override
  public void run() {
    inputUsernamePassword();
  }

  public void inputUsernamePassword() {
    var lock = usernamePasswordToken.getLock();
    var readInputCondition = usernamePasswordToken.getReadInputCondition();
    var validatedCondition = usernamePasswordToken.getValidatedCondition();
    lock.lock();
    try {
      log.info("Please input username:");
      usernamePasswordToken.setUsername(scanner.nextLine());
      log.info("Please input password:");
      usernamePasswordToken.setPassword(scanner.nextLine());
      readInputCondition.signal();
      try {
        validatedCondition.await();
        if (!usernamePasswordToken.isValidated()) {
          inputUsernamePassword();
        }
      } catch (InterruptedException e) {
        log.info("Validated condition is interrupted");
      }
    } finally {
      lock.unlock();
    }
  }
}
