package vn.io.vutiendat3601.javaconcurrency.threadsharing.problem.racecondition;

public class InventoryCounter {
  private long numberOfItems = 0;

  public void increment() {
    numberOfItems++;
  }

  public void decrement() {
    numberOfItems--;
  }

  public long getItems() {
    return numberOfItems;
  }
}
