package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.waitnotify.matrixmultiplier;

import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadSafeQueue {
  private Queue<MatrixPair> queue = new LinkedList<>();
  private boolean isEmpty = true;
  private boolean isTerminate = false;
  private static final int CAPACITY = 5;

  public synchronized void offer(MatrixPair matrixPair) {
    // Uncomment to enable backpressure
    //  while (queue.size() == CAPACITY) {
    //    try {
    //      wait();
    //    } catch (InterruptedException e) {
    //    }
    //  }
    queue.offer(matrixPair);
    isEmpty = false;
    notify();
  }

  public synchronized MatrixPair poll() {
    var ans = (MatrixPair) null;
    while (isEmpty && !isTerminate) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }

    if (queue.size() == 1) {
      isEmpty = true;
    }

    if (queue.size() == 0 && isTerminate) {
      return null;
    }

    log.info("Queue size " + queue.size());

    ans = queue.poll();
    if (queue.size() < CAPACITY) {
      notifyAll();
    }
    return ans;
  }

  public synchronized void terminate() {
    isTerminate = true;
    notifyAll();
  }
}
