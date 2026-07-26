package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import java.math.BigInteger;

public class LongComputationWorkerApplication {
  public static void main(String[] args) throws InterruptedException {
    var worker =
        new Thread(
            new LongComputationWorker(new BigInteger("20000"), new BigInteger("10000")));
    worker.setDaemon(true);
    worker.start();
  }
}
