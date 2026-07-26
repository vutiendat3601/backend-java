package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import java.util.ArrayList;
import java.util.Random;

public class PasswordGuessingApplication {
  public static void main(String[] args) {
    var random = new Random();
    var vault = new Vault(random.nextInt());
    // var vault = new Vault(100);
    var threads = new ArrayList<Thread>();
    var ascendingHackerThread = new AscendingHackerThread(vault);
    ascendingHackerThread.setName("ascendingHackerThread");
    var descendingHackerThread = new DescendingHackerThread(vault);
    descendingHackerThread.setName("descendingHackerThread");
    var policeThread = new PoliceThread();
    policeThread.setName("policeThread");

    threads.add(ascendingHackerThread);
    threads.add(descendingHackerThread);
    threads.add(policeThread);
    threads.forEach(Thread::start);
  }
}
