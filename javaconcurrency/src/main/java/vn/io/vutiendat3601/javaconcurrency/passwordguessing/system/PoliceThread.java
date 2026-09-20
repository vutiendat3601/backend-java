package vn.io.vutiendat3601.javaconcurrency.passwordguessing.system;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoliceThread extends Thread {
  @Override
  public void run() {
    int i = 10;
    while (i > 0) {
      log.info("Time: {}", i);
      try {
        Thread.sleep(10_000); // 10 seconds.
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      i--;
    }
    log.info("[{}] Game over for you hackers.", getName());
    System.exit(0);
  }
}
