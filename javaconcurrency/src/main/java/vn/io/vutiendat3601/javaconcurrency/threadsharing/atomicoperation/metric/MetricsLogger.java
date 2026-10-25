package vn.io.vutiendat3601.javaconcurrency.threadsharing.atomicoperation.metric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MetricsLogger extends Thread {
  private final Metrics metrics;

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(100);
        double currentAverage = metrics.getAverage();
        log.info("currentAverage={}", currentAverage);
      } catch (InterruptedException e) {
        log.error("Error happened in MetricsLogger", e);
      }
    }
  }
}
