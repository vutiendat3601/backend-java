package vn.io.vutiendat3601.javaconcurrency.threadsharing.atomicoperation.metric;


public class MetricsApplication {
  public static void main(String[] args) {
    var metrics = new Metrics();
    var businessLogic = new BusinessLogic(metrics);
    var businessLogic2 = new BusinessLogic(metrics);
    var metricsLogger = new MetricsLogger(metrics);
    businessLogic.start();
    businessLogic2.start();
    metricsLogger.start();
  }
}
