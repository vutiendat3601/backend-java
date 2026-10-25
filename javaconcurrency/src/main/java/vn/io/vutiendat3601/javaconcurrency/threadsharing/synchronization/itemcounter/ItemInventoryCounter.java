package vn.io.vutiendat3601.javaconcurrency.threadsharing.synchronization.itemcounter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemInventoryCounter {
  private long numberOfItems = 0;

  // aquireLock(this)
  public synchronized void increment() {
    numberOfItems++;
    log.info("Added one item numberOfItems={}", numberOfItems);
  }

  // aquireLock(this)
  public synchronized void decrement() {
    numberOfItems--;
    log.info("Removed one item numberOfItems={}", numberOfItems);
  }

  // aquireLock(this)
  public synchronized long getNumberOfItems() {
    return numberOfItems;
  }
}
