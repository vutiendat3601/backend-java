package vn.io.vutiendat3601.javaconcurrency.session06threadsharing.deadlock;

public class TrainApplication {
  public static void main(String[] args) {
    var intersection = new Intersection();
    var trainA = new TrainA(intersection);
    var trainB = new TrainB(intersection);
    trainA.start();
    trainB.start();
  }
}
