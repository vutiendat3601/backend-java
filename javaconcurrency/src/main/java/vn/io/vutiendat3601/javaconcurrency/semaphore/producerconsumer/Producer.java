package vn.io.vutiendat3601.javaconcurrency.semaphore.producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Producer extends Thread {
  private final Semaphore full;
  private final Semaphore empty;
  private final BlockingQueue<String> queue;

  {
    setName(Producer.class.getSimpleName());
  }

  @Override
  public void run() {
    produceDummyMessage();
    while (true) {}
  }

  public void produceMessage(String message) {
    try {
      empty.acquire();
      queue.offer(message);
      full.release();
    } catch (InterruptedException e) {
    }
  }

  private void produceDummyMessage() {
    var random = new Random();
    var c = 'A';
    while (true) {
      if (c > 'Z') {
        break;
      }
      produceMessage(String.valueOf(c));
      c++;
      try {
        Thread.sleep(random.nextInt(1000));
      } catch (InterruptedException e) {
      }
    }
  }
}
