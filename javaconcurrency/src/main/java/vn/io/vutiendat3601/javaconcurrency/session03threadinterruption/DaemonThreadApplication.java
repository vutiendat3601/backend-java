package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import java.math.BigInteger;

public class DaemonThreadApplication {
  public static void main(String[] args) {
    var thread =
        new Thread(
            new LongComputationTask(new BigInteger("20000000"), new BigInteger("100000000000000")));
    thread.setDaemon(true);
    thread.start();
  }
}
