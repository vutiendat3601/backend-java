package vn.io.vutiendat3601.javaconcurrency.threadtermination.daemonthread;

import java.math.BigInteger;
import java.time.Duration;

public class DaemonThreadApplication {
  public static void main(String[] args) throws InterruptedException {
    var base = BigInteger.valueOf(20_000);
    var power = BigInteger.valueOf(1_000_000_000);
    var longComputationTaskDaemonThread = new LongComputationTaskDaemonThread(base, power);
    // longComputationTaskDaemonThread.setDaemon(false); // Uncomment if you want to test non-daemon
    longComputationTaskDaemonThread.start();
    Thread.sleep(Duration.ofMillis(1_000));
    // longComputationTaskDaemonThread.interrupt(); // Uncomment if you want to test non-daemon
  }
}
