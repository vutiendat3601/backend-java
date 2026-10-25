package vn.io.vutiendat3601.javaconcurrency.threadsharing.visibility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotSynchronizedApplication {
  private static boolean running = true;

  public static void main(String[] args) throws InterruptedException {
    var thread =
        new Thread(
            () -> {
              long count = 0;
              
              while (running) {
                // Requires no IO/synchronized inside loop to raise visibility issue.
                count++;
              }
              log.info("Application stopped. Total loops: {}", count);
            });
    thread.start();

    Thread.sleep(1000);
    running = false;
    log.info("Main thread set running to false.");
  }
}
