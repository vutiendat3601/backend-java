package vn.io.vutiendat3601.javaconcurrency.threadsharing.atomicoperation.metric;

import java.util.Random;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BusinessLogic extends Thread {
  private final Metrics metrics;
  private final Random random = new Random();

  @Override
  public void run() {
    while (true) {
      try {
        var start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10));
        var end = System.currentTimeMillis();
        metrics.addSample(end - start);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
