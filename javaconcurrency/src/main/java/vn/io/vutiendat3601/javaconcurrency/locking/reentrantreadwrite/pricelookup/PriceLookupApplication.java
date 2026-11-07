package vn.io.vutiendat3601.javaconcurrency.locking.reentrantreadwrite.pricelookup;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceLookupApplication {
  private static final int HIGHEST_PRICE = 1000;

  public static void main(String[] args) {
    var inventoryDatabase = new InventoryDatabase();
    var random = new Random();
    for (int i = 0; i < 100_000; i++) {
      inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE + 1));
    }

    var writeThread =
        new Thread(
            () -> {
              while (true) {
                inventoryDatabase.addItem(HIGHEST_PRICE + 1);
                inventoryDatabase.removeItem(HIGHEST_PRICE + 1);
                try {
                  Thread.sleep(10);
                } catch (InterruptedException e) {
                }
              }
            });
    writeThread.setDaemon(true);
    writeThread.start();

    var numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
    var readThreads = new ArrayList<Thread>();
    for (int i = 0; i < numberOfThreads; i++) {
      var readThread =
          new Thread(
              () -> {
                for (int j = 0; j < 100_000; j++) {
                  var upperBoundPrice = random.nextInt(HIGHEST_PRICE + 1);
                  var lowerBoundPrice =
                      upperBoundPrice == 0 ? 0 : random.nextInt(upperBoundPrice + 1);
                  inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
              });
      readThread.setDaemon(true);
      readThreads.add(readThread);
    }

    var startReadingTime = Instant.now();
    for (var readThread : readThreads) {
      readThread.start();
    }
    for (var readThread : readThreads) {
      try {
        readThread.join();
      } catch (InterruptedException e) {
      }
    }
    var endReadingTime = Instant.now();
    log.info("Reading took {}ms", Duration.between(startReadingTime, endReadingTime).toMillis());
  }
}
