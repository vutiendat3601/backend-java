package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.semaphore.ratelimit;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThirdPartyClient {
  private final Semaphore semaphore;
  private final AtomicLong count = new AtomicLong();

  public ThirdPartyClient(int maxConcurrentRequest) {
    semaphore = new Semaphore(maxConcurrentRequest);
  }

  public boolean get() {
    try {
      semaphore.acquire();
      try {
        count.incrementAndGet();
        log.info(
            "Reached the function \"get\" , count={}, threadName={}",
            count,
            Thread.currentThread().getName());
        Thread.sleep(3_000);
        log.info(
            "Done the function \"get\" , count={}, threadName={}",
            count,
            Thread.currentThread().getName());
        return true;
      } finally {
        semaphore.release();
      }
    } catch (InterruptedException e) {
    }
    return false;
  }
}
