package vn.io.vutiendat3601.javaconcurrency.semaphore.ratelimit;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateLimitApplication {
  public static void main(String[] args) {
    var thirdPartyClient = new ThirdPartyClient(3);
    var numberOfRequests = 10;

    var threads = new ArrayList<Thread>();
    var runnable = (Runnable) () -> thirdPartyClient.get();
    for (int i = 0; i < numberOfRequests; i++) {
      var thread = new Thread(runnable);
      thread.setName("client-" + i);
      threads.add(thread);
    }
    for (var thread : threads) {
      thread.start();
    }

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
                if (stateToThreadNames.get(State.TERMINATED).size() == numberOfRequests) {
                  break;
                }
                try {
                  Thread.sleep(2_500);
                } catch (InterruptedException e) {
                }
              }
            })
        .start();
  }
}
