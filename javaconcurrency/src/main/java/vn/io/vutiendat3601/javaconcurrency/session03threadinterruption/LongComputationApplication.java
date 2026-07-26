package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import java.math.BigInteger;

public class LongComputationApplication {
  public static void main(String[] args) throws InterruptedException {
    var thread =
        new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("1000000000")));
    thread.start();
    Thread.sleep(1000);
    thread.interrupt();
  }
}
