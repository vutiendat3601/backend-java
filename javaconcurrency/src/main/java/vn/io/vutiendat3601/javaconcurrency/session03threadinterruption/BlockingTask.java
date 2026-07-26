package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockingTask implements Runnable {

  @Override
  public void run() {
    try {
      Thread.sleep(50_000_000);
    } catch (InterruptedException e) {
      log.info("Exiting blocking thread");
    }
  }
}
