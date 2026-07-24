package vn.io.vutiendat3601.rmihandson.client;

import java.rmi.registry.LocateRegistry;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.rmihandson.common.Calculator;

@Slf4j
public class Client {
  public static void main(String[] args) throws Exception {
    var registry = LocateRegistry.getRegistry("localhost", 1099);

    var calculator = (Calculator) registry.lookup("CalculatorService");
    var addition = calculator.add(5, 7);
    var subtraction = calculator.subtract(20, 8);
    log.info("addition = {}", addition);
    log.info("subtraction = {}", subtraction);
  }
}
