package vn.io.vutiendat3601.javaconcurrency.threadsharing.problem.deadlock;

public class TrainApplication {
  public static void main(String[] args) {
    var intersection = new Intersection();
    var trainA = new Thread(new TrainA(intersection));
    var trainB = new Thread(new TrainB(intersection));
    trainA.start();
    trainB.start();
  }
}
