package vn.io.vutiendat3601.javaconcurrency.threadtermination.longcomputationtask;

import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongComputationTaskThread extends Thread {
  private final BigInteger base;
  private final BigInteger power;

  public LongComputationTaskThread(BigInteger base, BigInteger power) {
    super();
    this.base = base;
    this.power = power;
    setName(LongComputationTaskThread.class.getSimpleName());
  }

  @Override
  public void run() {
    log.info("{}^{} = {}", base, power, pow(base, power));
  }

  private BigInteger pow(BigInteger base, BigInteger power) {
    var answer = BigInteger.ONE;
    for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
      if (Thread.currentThread().isInterrupted()) {
        // Handling the interrupt signal explicitly, so that this thread can be interrupted

        log.info("[{}] LongComputationTaskThread was interrupted.", getName());
        return BigInteger.ZERO;
      }
      answer = answer.multiply(base);
      log.info("[{}] Calculated {}^{}", getName(), base, i);
    }
    return answer;
  }
}
