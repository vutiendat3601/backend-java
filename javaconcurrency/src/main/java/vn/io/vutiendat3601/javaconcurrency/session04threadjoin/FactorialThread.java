package vn.io.vutiendat3601.javaconcurrency.session04threadjoin;

import java.math.BigInteger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class FactorialThread extends Thread {
  private final long input;
  private BigInteger answer = BigInteger.ZERO;
  private boolean isFinished = false;

  @Override
  public void run() {
    answer = factorial(input);
    isFinished = true;
  }

  public BigInteger factorial(long n) {
    BigInteger ans = BigInteger.ONE;
    for (long i = n; i > 0; i--) {
      ans = ans.multiply(BigInteger.valueOf(i));
    }
    return ans;
  }
}
