package vn.io.vutiendat3601.javaconcurrency.session04threadjoin;

import java.util.Random;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class FileLoadingThread extends Thread {
  private int progress = 0;

  @Override
  public void run() {
    try {
      final var random = new Random();
      while (progress < 100) {
        Thread.sleep(1500);
        if (100 - progress < 10) {
          progress = 100;
        } else {
          progress += random.nextInt(10);
        }
      }
    } catch (InterruptedException e) {
    }
  }
}
