package vn.io.vutiendat3601.javaconcurrency.locking.reentrant.price;

import java.util.Random;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class PriceUpdater extends Thread {
  {
    setName(getClass().getSimpleName());
  }

  private final PriceContainer priceContainer;

  @Override
  public void run() {
    final var random = new Random();
    while (true) {
      priceContainer.getLock().lock();

      try {
        try {
          Thread.sleep(10_000);
        } catch (InterruptedException e) {
          log.info("{} is interrupted", getName());
        }
        priceContainer.setBitcoinPrice(random.nextDouble(1));
        priceContainer.setEtherPrice(random.nextDouble(10));
        priceContainer.setLitecoinPrice(random.nextDouble(100));
        priceContainer.setBitcoinCashPrice(random.nextDouble(1_000));
        priceContainer.setRipplePrice(random.nextDouble(10_000));
      } finally {
        priceContainer.getLock().unlock();
      }

      try {
        Thread.sleep(1_000);
      } catch (InterruptedException e) {
        log.info("{} is interrupted", getName());
      }
    }
  }
}
