package vn.io.vutiendat3601.javaconcurrency.threadsharing.datarace;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SharedObject {
  private long x;
  private long y;

  // in some case, jvm, runtime environment, compiler-optimization:
  // The y++ is executed before x++
  public void increment() {
    x++;
    y++;
  }

  public void checkForDataRace() {
    if (y > x) {
      log.info("y > x => Data race is detected.");
    }
  }
}
