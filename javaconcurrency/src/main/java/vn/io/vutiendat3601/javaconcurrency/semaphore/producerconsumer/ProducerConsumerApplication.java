package vn.io.vutiendat3601.javaconcurrency.semaphore.producerconsumer;

import java.lang.Thread.State;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerConsumerApplication {
  public static void main(String[] args) {
    var fullSema = new Semaphore(0);
    var emptySema = new Semaphore(1);
    var queue = new LinkedBlockingQueue<String>();
    var producer = new Producer(fullSema, emptySema, queue);
    var consumer = new Consumer(fullSema, emptySema, queue);
    var input =
        new Thread(
            () -> {
              while (true) {
                var message = IO.readln();
                producer.produceMessage(message);
              }
            });
    input.setName("Input");
    producer.start();
    consumer.start();
    input.start();
    var threads = List.of(producer, consumer, input);
    var tracker =
        new Thread(
            () -> {
              while (true) {
                var stateToThreadNames = new HashMap<State, Set<String>>();
                for (var state : State.values()) {
                  stateToThreadNames.put(state, new TreeSet<>());
                }
                for (var thread : threads) {
                  stateToThreadNames.get(thread.getState()).add(thread.getName());
                }
                IO.print("\n".repeat(3));
                log.info("-".repeat(20));
                for (var state : State.values()) {
                  log.info("State {}: {}", state, stateToThreadNames.get(state));
                }
                log.info("-".repeat(50));
                try {
                  Thread.sleep(2_500);
                } catch (InterruptedException e) {
                }
              }
            });
    tracker.start();
  }
}
