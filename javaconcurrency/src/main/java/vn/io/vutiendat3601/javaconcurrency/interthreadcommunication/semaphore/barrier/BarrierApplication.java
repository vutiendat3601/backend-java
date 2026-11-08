package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.semaphore.barrier;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BarrierApplication {
  public static void main(String[] args) {
    var numberOfWorkers = 3;
    var barrier = new Barrier(numberOfWorkers);
    var threads = new ArrayList<Thread>();
    for (int i = 0; i < numberOfWorkers; i++) {
      var thread =
          new Thread(
              () -> {
                log.info("[{}]: part 1 of the work is finished", Thread.currentThread().getName());
                barrier.waitForOthers();
                log.info("[{}]: part 2 of the work is finished", Thread.currentThread().getName());
              });
      thread.setName("worker-" + i);
      threads.add(thread);
    }

    for (var thread : threads) {
      thread.start();
    }
  }
}
