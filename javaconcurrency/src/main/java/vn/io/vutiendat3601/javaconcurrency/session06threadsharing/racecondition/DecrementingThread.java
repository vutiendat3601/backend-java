package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.racecondition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class DecrementingThread extends Thread {
  private final InventoryCounter inventoryCounter;

  @Override
  public void run() {
    for (int i = 0; i < 10_000; i++) {
      inventoryCounter.decrement();
    }
  }
}
