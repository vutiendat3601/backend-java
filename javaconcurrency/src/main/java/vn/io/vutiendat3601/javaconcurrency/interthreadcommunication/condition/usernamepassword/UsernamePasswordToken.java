package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.condition.usernamepassword;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UsernamePasswordToken {
  private final Lock lock = new ReentrantLock();
  private final Condition readInputCondition = lock.newCondition();
  private String username;
  private String password;

  private final Condition validatedCondition = lock.newCondition();
  private boolean isValidated;
}
