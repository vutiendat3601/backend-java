import java.util.ArrayList;

public class RaceCondition {
  private int balance = 0;

  public void increaseBalance(String threadId) {
    balance++; // This is not a atomic operation.
    System.out.println("Applied thread %s, balance=%d".formatted(threadId, balance));
  }

  public void decreaseBalance(String threadId) {
    balance--; // This is not a atomic operation.
    System.out.println("Applied thread %s, balance=%d".formatted(threadId, balance));
  }

  public int getBalance() {
    return balance;
  }

  public static void main(String[] args) throws InterruptedException {
    var threads = new ArrayList<Thread>();
    var raceCondition = new RaceCondition();
    final var NUM_OF_THREADS = 100000;
    for (int i = 0; i < NUM_OF_THREADS; i++) {
      var threadId = "T" + i;
      var thread =
          new Thread(
              () -> {
                raceCondition.increaseBalance(threadId);
                raceCondition.decreaseBalance(threadId);
              });
      thread.setName(threadId);
      threads.add(thread);
      thread.start();
    }
    for (var thread : threads) {
      thread.join();
    }
    System.out.println("Balance: " + raceCondition.getBalance());
  }
}
