package vn.io.vutiendat3601.javaconcurrency.threadtermination.blockingtask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockingTaskThread extends Thread {

  @Override
  public void run() {
    try {
      // Sleep can throw a InterruptedException, so that the thread can be interrupted
      Thread.sleep(50_000_000);
    } catch (InterruptedException e) {
      log.info("[{}] Exiting blocking thread {}", getName(), e);
    }
  }
}
