package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import java.time.Duration;

public class BlockingTaskApplication {
  public static void main(String[] args) throws InterruptedException {
    var thread = new Thread(new BlockingTask());
    thread.start();
    Thread.sleep(Duration.ofSeconds(2));
    thread.interrupt();
  }
}
