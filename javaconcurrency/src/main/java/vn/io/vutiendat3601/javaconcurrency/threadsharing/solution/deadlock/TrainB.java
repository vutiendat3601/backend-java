package vn.io.vutiendat3601.javaconcurrency.threadsharing.solution.deadlock;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TrainB implements Runnable {
  private final Intersection intersection;
  private final Random random = new Random();

  @Override
  public void run() {
    while (true) {
      var sleepingTime = random.nextLong(5);
      try {
        Thread.sleep(sleepingTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      intersection.takeRoadB();
    }
  }
}
