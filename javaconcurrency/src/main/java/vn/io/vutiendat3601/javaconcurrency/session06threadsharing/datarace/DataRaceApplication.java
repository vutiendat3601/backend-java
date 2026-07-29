package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.datarace;

public class DataRaceApplication {
  public static void main(String[] args) {
    final var sharedObject = new SharedObject();
    var thread1 =
        new Thread(
            () -> {
              for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedObject.increment();
              }
            });
    var thread2 =
        new Thread(
            () -> {
              for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedObject.checkForDataRace();
              }
            });
    thread1.start();
    thread2.start();
  }
}
