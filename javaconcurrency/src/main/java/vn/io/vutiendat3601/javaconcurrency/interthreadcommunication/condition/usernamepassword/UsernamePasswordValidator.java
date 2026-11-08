package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.condition.usernamepassword;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UsernamePasswordValidator extends Thread {
  private final UsernamePasswordToken usernamePasswordToken;

  @Override
  public void run() {
    validateUsernamePasswordInBackground();
  }

  public void validateUsernamePasswordInBackground() {
    var lock = usernamePasswordToken.getLock();
    var readInputCondition = usernamePasswordToken.getReadInputCondition();
    var validatedCondition = usernamePasswordToken.getValidatedCondition();
    lock.lock();
    try {
      var username = (String) null;
      var password = (String) null;
      while ((username == null || username.isBlank()) || (password == null || password.isBlank())) {
        if (username != null || password != null) {
          log.info("Waiting for not blank username or password ...");
        }
        try {
          usernamePasswordToken.setValidated(false);
          validatedCondition.signal();
          readInputCondition.await(); // Unlock, will try to acquire lock after signal
          username = usernamePasswordToken.getUsername();
          password = usernamePasswordToken.getPassword();
        } catch (InterruptedException e) {
          log.info("Condition await is interrupted");
        }
      }
      usernamePasswordToken.setValidated(true);
      validatedCondition.signal();
      log.info(
          "Validated user and password successfully, username={}, password={}",
          usernamePasswordToken.getUsername(),
          usernamePasswordToken.getPassword());
    } finally {
      lock.unlock();
    }
  }
}
