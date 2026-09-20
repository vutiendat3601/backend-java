package vn.io.vutiendat3601.javaconcurrency.threadtermination.daemonthread;

import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongComputationTaskDaemonThread extends Thread {
  private final BigInteger base;
  private final BigInteger power;

  public LongComputationTaskDaemonThread(BigInteger base, BigInteger power) {
    super();
    this.base = base;
    this.power = power;
    setName(LongComputationTaskDaemonThread.class.getSimpleName());
    setDaemon(true);
  }

  @Override
  public void run() {
    log.info("{}^{} = {}", base, power, pow(base, power));
  }

  private BigInteger pow(BigInteger base, BigInteger power) {
    var answer = BigInteger.ONE;
    for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
      answer = answer.multiply(base);
      log.info("Calculated {}^{}", base, i);
    }
    return answer;
  }
}
