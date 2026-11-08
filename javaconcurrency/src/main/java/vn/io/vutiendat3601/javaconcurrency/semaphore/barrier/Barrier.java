package vn.io.vutiendat3601.javaconcurrency.semaphore.barrier;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Barrier {
  private final int numberOfWorkers;
  private final Semaphore semaphore = new Semaphore(0);
  private final AtomicInteger counter = new AtomicInteger(0);

  public void waitForOthers() {
    var count = counter.incrementAndGet();
    boolean isLastWorker = count == numberOfWorkers;
    if (isLastWorker) {
      semaphore.release(numberOfWorkers - 1);
    } else {
      try {
        semaphore.acquire();
      } catch (InterruptedException e) {
      }
    }
  }
}
