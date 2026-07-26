package vn.io.vutiendat3601.javaconcurrency.session03threadinterruption;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LongComputationWorker implements Runnable {
  private BigInteger base;
  private BigInteger power;

  @Override
  public void run() {
    log.info("{}^{} = {}", base, power, pow(base, power));
  }

  private BigInteger pow(BigInteger base, BigInteger power) {
    var answer = BigInteger.ONE;
    for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
      log.info("Calculating {}^{}", base, power);
      answer = answer.multiply(base);
    }
    return answer;
  }
}
