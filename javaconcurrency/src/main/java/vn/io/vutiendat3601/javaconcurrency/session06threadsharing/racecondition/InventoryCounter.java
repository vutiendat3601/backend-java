package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.racecondition;

public class InventoryCounter {
  private long items = 0;
  private Object lock = new Object();

  public void increment() {
    synchronized (lock) {
      items++;
    }
  }

  public void decrement() {
    synchronized (lock) {
      items--;
    }
  }

  public long getItems() {
    synchronized (lock) {
      return items;
    }
  }
}
