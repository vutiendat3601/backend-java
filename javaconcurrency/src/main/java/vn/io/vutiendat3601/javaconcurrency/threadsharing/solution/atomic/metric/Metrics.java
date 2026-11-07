package vn.io.vutiendat3601.javaconcurrency.threadsharing.solution.atomic.metric;

public class Metrics {
  private volatile long count = 0L;
  private volatile double average = 0.0D;

  public synchronized void addSample(long sample) {
    double currentSum = average * count;
    count++;
    average = (currentSum + sample) / count;
  }

  // Getter / Setter is atomic
  public double getAverage() {
    return average;
  }
}
