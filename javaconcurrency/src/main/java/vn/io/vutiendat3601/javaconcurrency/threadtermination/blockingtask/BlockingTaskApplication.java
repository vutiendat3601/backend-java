package vn.io.vutiendat3601.javaconcurrency.threadtermination.blockingtask;

import java.time.Duration;

public class BlockingTaskApplication {
  public static void main(String[] args) throws InterruptedException {
    var blockingTaskThread = new BlockingTaskThread();
    blockingTaskThread.start();
    // The application keep running because BlockingTaskThread is still running
    Thread.sleep(Duration.ofSeconds(2));

    // Request thread to be stopped
    blockingTaskThread.interrupt();
  }
}
