package vn.io.vutiendat3601.javaconcurrency.threadsharing.solution.deadlock;

public class TrainApplication {
  public static void main(String[] args) {
    var intersection = new Intersection();
    var trainA = new Thread(new TrainA(intersection));
    trainA.setName("Train A");
    var trainB = new Thread(new TrainB(intersection));
    trainB.setName("Train B");
    
    trainA.start();
    trainB.start();
  }
}
