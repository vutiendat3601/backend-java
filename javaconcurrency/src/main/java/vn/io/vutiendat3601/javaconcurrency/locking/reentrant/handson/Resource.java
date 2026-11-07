package vn.io.vutiendat3601.javaconcurrency.locking.reentrant.handson;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Resource {
  private final Lock lock = new ReentrantLock();

  public void testLock() {
    log.info("{} aquiring lock", Thread.currentThread().getName());
    lock.lock();
    try {
      log.info("{} is acquired lock", Thread.currentThread().getName());
      Thread.sleep(3_000);
    } catch (InterruptedException e) {
      log.info("{} is interrupted", Thread.currentThread().getName());
    } finally {
      lock.unlock();
    }
  }

  public void testLockInterruptibly() {
    try {
      log.info("{} is aquiring lock", Thread.currentThread().getName());
      lock.lockInterruptibly();
      try {
        log.info("{} is acquired lock", Thread.currentThread().getName());
        Thread.sleep(3_000);
      } finally {
        lock.unlock();
      }
    } catch (InterruptedException e) {
      log.info("{} is interrupted", Thread.currentThread().getName());
    }
  }

  public void testTryLock() {
    log.info("{} is aquiring lock", Thread.currentThread().getName());
    if (lock.tryLock()) {
      try {
        log.info("{} is acquired lock", Thread.currentThread().getName());
        Thread.sleep(3_000);
      } catch (Exception e) {
        log.info("{} is interrupted", Thread.currentThread().getName());
      } finally {
        lock.unlock();
      }
    } else {
      log.info("{} is failed to acquire lock", Thread.currentThread().getName());
      log.info("{} is continuing task ...", Thread.currentThread().getName());
    }
  }
}
