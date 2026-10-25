package vn.io.vutiendat3601.javaconcurrency.threadsharing.problem.racecondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryApplication {
  public static void main(String[] args) throws InterruptedException {
    var inventoryCounter = new InventoryCounter();
    var incrementingThread = new IncrementThread(inventoryCounter);
    var decrementingThread = new DecrementThread(inventoryCounter);
    incrementingThread.start();
    decrementingThread.start();

    incrementingThread.join();
    decrementingThread.join();

    log.info("We currently have {} items.", inventoryCounter.getItems());
  }
}
