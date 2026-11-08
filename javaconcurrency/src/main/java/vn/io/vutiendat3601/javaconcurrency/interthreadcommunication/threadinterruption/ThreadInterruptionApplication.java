package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.threadinterruption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadInterruptionApplication {
  public static void main(String[] args) throws InterruptedException {
    var thread1 =
        new Thread(
            () -> {
              try {
                Thread.sleep(10_000);
              } catch (InterruptedException e) {
                log.info("[{}] sleep is interrupted", Thread.currentThread().getName());
              }
            });
    thread1.setName("thread1");
    thread1.start();
    Thread.sleep(1_000);
    var thread2 = new Thread(() -> thread1.interrupt());
    thread2.start();
  }
}
