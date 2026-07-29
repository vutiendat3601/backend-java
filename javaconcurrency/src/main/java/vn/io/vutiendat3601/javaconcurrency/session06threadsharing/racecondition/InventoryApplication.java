package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.racecondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryApplication {
  public static void main(String[] args) throws InterruptedException {
    var inventoryCounter = new InventoryCounter();
    var incrementingThread = new IncrementingThread(inventoryCounter);
    var decrementingThread = new DecrementingThread(inventoryCounter);
    incrementingThread.start();
    decrementingThread.start();

    incrementingThread.join();
    decrementingThread.join();

    log.info("We currently have {} items.", inventoryCounter.getItems());
  }
}
