package vn.io.vutiendat3601.javaconcurrency.locking.reentrant.handson;

public class HansOnReentrantLockApplication {
  public static void main(String[] args) throws InterruptedException {
    var resource = new Resource();
    var runnable = (Runnable) () -> resource.testLock();
    // var runnable = (Runnable) () -> resource.testLockInterruptibly();
    // var runnable = (Runnable) () -> resource.testTryLock();

    var thread1 = new Thread(runnable);
    thread1.setName("thread1");
    var thread2 = new Thread(runnable);
    thread2.setName("thread2");
    thread2.start();
    thread1.start();
  }
}
