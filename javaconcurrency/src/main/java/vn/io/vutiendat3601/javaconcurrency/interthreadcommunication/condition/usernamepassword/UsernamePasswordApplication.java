package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.condition.usernamepassword;

import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernamePasswordApplication {
  public static void main(String[] args) {
    var usernamePasswordToken = new UsernamePasswordToken();
    var threadValidator = new UsernamePasswordValidator(usernamePasswordToken);
    var scanner = new Scanner(System.in);
    var threadReader = new UsernamePasswordReader(usernamePasswordToken, scanner);
    threadValidator.start();
    threadReader.start();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> scanner.close()));
  }
}
