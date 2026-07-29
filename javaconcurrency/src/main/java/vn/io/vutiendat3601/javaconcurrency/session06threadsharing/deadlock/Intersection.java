package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.deadlock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Intersection {
  private Object roadA = new Object();
  private Object roadB = new Object();

  public void takeRoadA() {
    synchronized (roadA) {
      log.info("Road A is locked by thread {}", Thread.currentThread().getName());
      synchronized (roadB) {
        log.info("Train is passing through road A");
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
        }
      }
    }
  }

  public void takeRoadB() {
    synchronized (roadB) {
      log.info("Road B is locked by thread {}", Thread.currentThread().getName());
      synchronized (roadA) {
        log.info("Train is passing through road B");
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
