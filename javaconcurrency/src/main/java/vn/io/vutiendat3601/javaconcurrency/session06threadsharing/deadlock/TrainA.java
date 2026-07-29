package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.deadlock;

import java.util.Random;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrainA extends Thread {
  private final Intersection intersection;
  private Random random = new Random();

  @Override
  public void run() {
    while (true) {
      long sleepingTime = random.nextInt(5);
      try {
        Thread.sleep(sleepingTime);
      } catch (InterruptedException e) {
      }
      intersection.takeRoadA();
    }
  }
}
