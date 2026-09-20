package vn.io.vutiendat3601.javaconcurrency.threadjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadJoinApplication {
  public static void main(String[] args) throws InterruptedException {
    var inputs = Arrays.asList(1L, 999999L, 3L, 4L, 1000000000000000000L);
    var threads = new ArrayList<FactorialThread>();
    inputs.stream().map(FactorialThread::new).forEach(threads::add);
    threads.forEach(Thread::start);
    Queue<FactorialThread> queue = new LinkedList<FactorialThread>();
    queue.addAll(threads);
    while (!queue.isEmpty()) {
      var thread = queue.poll();
      thread.join(1_000);
      if (thread.isFinished()) {
        log.info("Factorial of {} is {}.", thread.getInput(), thread.getAnswer());
      } else {
        log.info("The calculation for {} is still in progress.", thread.getInput());
        queue.offer(thread);
      }
    }
    log.info("All tasks were finished.");
  }
}
