package vn.io.vutiendat3601.javaconcurrency.threadsharing.synchronization.itemcounter2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemInventoryApplication {
  public static void main(String[] args) throws InterruptedException {
    var inventoryCounter = new ItemInventoryCounter();
    var incrementingThread = new ItemIncrementThread(inventoryCounter);
    var decrementingThread = new ItemDecrementThread(inventoryCounter);
    incrementingThread.start();
    decrementingThread.start();

    incrementingThread.join();
    decrementingThread.join();

    log.info("We currently have {} items.", inventoryCounter.getNumberOfItems());
  }
}
