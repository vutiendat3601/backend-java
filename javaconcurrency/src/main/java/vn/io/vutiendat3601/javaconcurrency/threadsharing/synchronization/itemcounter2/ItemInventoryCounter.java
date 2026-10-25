package vn.io.vutiendat3601.javaconcurrency.threadsharing.synchronization.itemcounter2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemInventoryCounter {
  private long numberOfItems = 0;
  private final Object lock = new Object();

  public void increment() {
    synchronized (lock) {
      numberOfItems++;
    }
  }

  public void decrement() {
    synchronized (lock) {
      numberOfItems--;
    }
  }

  public long getNumberOfItems() {
    synchronized (lock) {
      return numberOfItems;
    }
  }
}
