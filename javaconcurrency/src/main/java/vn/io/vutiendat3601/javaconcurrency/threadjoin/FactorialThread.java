package vn.io.vutiendat3601.javaconcurrency.threadjoin;

import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class FactorialThread extends Thread {
  private final long input;
  private boolean isFinished = false;
  private BigInteger answer = BigInteger.ZERO;

  public boolean isFinished() {
    return isFinished;
  }

  public BigInteger getAnswer() {
    return isFinished() ? answer : BigInteger.ZERO;
  }

  public long getInput() {
    return input;
  }

  @Override
  public void run() {
    factorial();
    isFinished = true;
  }

  @SneakyThrows
  private void factorial() {
    answer = BigInteger.valueOf(1);
    for (long i = input; i > 0; i--) {
      answer = answer.multiply(BigInteger.valueOf(i));
    }
  }
}
