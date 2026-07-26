package vn.io.vutiendat3601.javaconcurrency.session04threadjoin;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FactorialApplication {
  public static void main(String[] args) throws InterruptedException {
    var inputs = Arrays.asList(1000000000000000000L, 3435L, 2324L, 4656L, 23L, 5556L);
    var threads = new ArrayList<Thread>();
    inputs.stream().map(FactorialThread::new).forEach(threads::add);
    threads.forEach(t -> t.setDaemon(true));
    threads.forEach(Thread::start);
    threads.stream()
        .map(FactorialThread.class::cast)
        .forEach(
            t -> {
              try {
                t.join(3000, 100);
              } catch (InterruptedException e) {
              }
              if (t.isFinished()) {
                log.info("Factorial of {} is {}.", t.getInput(), t.getAnswer());
              } else {
                log.info("The calculation for {} is still in progress.", t.getInput());
              }
            });
  }
}
