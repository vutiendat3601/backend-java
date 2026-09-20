package vn.io.vutiendat3601.javaconcurrency.threadcreation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TheFirstThreadCreationApplication {
  public static void main(String[] args) throws InterruptedException {
    log.info("We're now in thread " + Thread.currentThread().getName() + ".");
    var thread =
        new Thread(
            () -> {
              var threadName = Thread.currentThread().getName();
              var threadPriority = Thread.currentThread().getPriority();
              log.info("[{}] We're now in thread {}.", threadName, threadName);
              log.info("[{}] Thread priority is {}.", threadName, threadPriority);
              throw new RuntimeException("Throws a unchecked exception.");
            });
    thread.setName("WORKER THREAD");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.setUncaughtExceptionHandler(
        (threadInstance, exception) ->
            log.error("Caught error in thread [{}]: {}", threadInstance.getName(), exception.getMessage()));
    thread.start();

    // During sleep time, the main thread is not consuming any CPU.
    Thread.sleep(1000);
  }
}
