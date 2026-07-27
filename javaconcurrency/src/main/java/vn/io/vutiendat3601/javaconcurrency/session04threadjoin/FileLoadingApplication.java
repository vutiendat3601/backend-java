package vn.io.vutiendat3601.javaconcurrency.session04threadjoin;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileLoadingApplication {
  public static void main() throws InterruptedException {
    var thread = new FileLoadingThread();
    thread.start();
    while (thread.getProgress() < 100) {
      log.info("Progress is {}%.", thread.getProgress());
      thread.join(Duration.ofMillis(2000));
    }
    log.info("Progress is {}%.", thread.getProgress());
  }
}
