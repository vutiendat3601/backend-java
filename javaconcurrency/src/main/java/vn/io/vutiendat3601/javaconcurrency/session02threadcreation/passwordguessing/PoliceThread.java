package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoliceThread extends Thread {
  @Override
  public void run() {
    int i = 10;
    while (i > 0) {
      log.info("Time: {}", i);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      i--;
    }
    log.info("Game over for you hackers.");
    System.exit(0);
  }
}
