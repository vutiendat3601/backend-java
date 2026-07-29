package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.datarace;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SharedObject {
  private long x;
  private long y;

  public void increment() {
    x++;
    y++;
  }

  public void checkForDataRace() {
    if (y > x) {
      log.info("y > x - Data race is detected.");
    }
  }
}
