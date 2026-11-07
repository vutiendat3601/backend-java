package vn.io.vutiendat3601.javaconcurrency.locking.reentrantreadwrite.pricelookup;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryDatabase {
  private final TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
  // private final Lock lock = new ReentrantLock();

  // Improved performance by using separate Read / Write lock
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private final Lock readLock = readWriteLock.readLock();
  private final Lock writeLock = readWriteLock.writeLock();

  public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
    // lock.lock();
    readLock.lock();
    try {
      var fromKey = priceToCountMap.ceilingKey(lowerBound);
      var toKey = priceToCountMap.ceilingKey(upperBound);
      if (fromKey == null || toKey == null) {
        return 0;
      }
      var rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);
      int ans = 0;
      for (var numberOfItemsForPrice : rangeOfPrices.values()) {
        ans += numberOfItemsForPrice;
      }
      return ans;
    } finally {
      // lock.unlock();
      readLock.unlock();
    }
  }

  public void addItem(int price) {
    // lock.lock();
    writeLock.lock();
    try {
      if (priceToCountMap.containsKey(price)) {
        priceToCountMap.put(price, priceToCountMap.get(price) + 1);
      } else {
        priceToCountMap.put(price, 1);
      }
    } finally {
      // lock.unlock();
      writeLock.unlock();
    }
  }

  public void removeItem(int price) {
    // lock.lock();
    writeLock.lock();
    try {
      if (priceToCountMap.containsKey(price)) {
        if (priceToCountMap.get(price) == 1) {
          priceToCountMap.remove(price);
        } else {
          priceToCountMap.put(price, priceToCountMap.get(price) - 1);
        }
      }
    } finally {
      // lock.unlock();
      writeLock.unlock();
    }
  }
}
