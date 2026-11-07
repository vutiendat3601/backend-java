package vn.io.vutiendat3601.javaconcurrency.threadsharing.solution.synchronization.itemcounter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemInventoryCounter {
  private long numberOfItems = 0;

  // acquireLock(this)
  public synchronized void increment() {
    numberOfItems++;
    log.info("Added one item numberOfItems={}", numberOfItems);
  }

  // acquireLock(this)
  public synchronized void decrement() {
    numberOfItems--;
    log.info("Removed one item numberOfItems={}", numberOfItems);
  }

  // acquireLock(this)
  public synchronized long getNumberOfItems() {
    return numberOfItems;
  }
}
