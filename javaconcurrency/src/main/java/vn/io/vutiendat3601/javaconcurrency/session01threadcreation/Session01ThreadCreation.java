package vn.io.vutiendat3601.javaconcurrency.session01threadcreation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Session01ThreadCreation {
  public static void main(String[] args) throws InterruptedException {
    log.info("We're now in thread " + Thread.currentThread().getName() + ".");
    var thread =
        new Thread(
            () -> {
              log.info("We're now in thread " + Thread.currentThread().getName() + ".");
              log.info("Current thread priority is " + Thread.currentThread().getPriority());
              throw new RuntimeException("Throw a unchecked exception.");
            });
    thread.setName("Worker thread 1");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.setUncaughtExceptionHandler(
        (threadInstance, exception) -> {
          log.error("Error in thread " + threadInstance.getName() + ": ", exception);
        });

    thread.start();

    Thread.sleep(1000);
  }
}
