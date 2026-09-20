package vn.io.vutiendat3601.javaconcurrency.threadtermination.longcomputationtask;

import java.math.BigInteger;

public class LongComputationApplication {
  public static void main(String[] args) throws InterruptedException {
    var base = BigInteger.valueOf(20_000);
    var power = BigInteger.valueOf(1_000_000_000);
    var longComputationTaskThread = new LongComputationTaskThread(base, power);
    longComputationTaskThread.start();
    Thread.sleep(1_000);
    longComputationTaskThread.interrupt();
  }
}
