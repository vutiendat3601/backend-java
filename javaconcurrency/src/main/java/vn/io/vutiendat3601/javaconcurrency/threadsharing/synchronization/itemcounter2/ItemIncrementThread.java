package vn.io.vutiendat3601.javaconcurrency.threadsharing.synchronization.itemcounter2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class ItemIncrementThread extends Thread {
  private final ItemInventoryCounter inventoryCounter;

  @Override
  public void run() {
    for (int i = 0; i < 10_000; i++) {
      inventoryCounter.increment();
    }
  }
}
