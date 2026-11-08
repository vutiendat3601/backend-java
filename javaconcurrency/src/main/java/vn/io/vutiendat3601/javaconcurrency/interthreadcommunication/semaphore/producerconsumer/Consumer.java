package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.semaphore.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Consumer extends Thread {
  private final Semaphore full;
  private final Semaphore empty;
  private final BlockingQueue<String> queue;

  {
    setName(Consumer.class.getSimpleName());
  }

  @Override
  public void run() {
    while (true) {
      try {
        log.info("Polling message ...");
        full.acquire();
        if (!queue.isEmpty()) {
          var message = queue.poll();
          log.info("Consumed message: {}", message);
        }
        empty.release();
      } catch (InterruptedException e) {
      }
    }
  }
}
