package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.threadjoin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadJoinApplication {
  public static void main(String[] args) throws InterruptedException {
    var thread1 =
        new Thread(
            () -> {
              try {
                Thread.sleep(3_000);
              } catch (InterruptedException e) {
                log.info("[{}] sleep is interrupted", Thread.currentThread().getName());
              }
            });
    thread1.setName("thread1");
    thread1.start();
    thread1.join();
  }
}
