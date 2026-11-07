package vn.io.vutiendat3601.javaconcurrency.locking.reentrant.price;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceContainer {
  private final Lock lock = new ReentrantLock();

  private double bitcoinPrice;
  private double etherPrice;
  private double litecoinPrice;
  private double bitcoinCashPrice;
  private double ripplePrice;
}
