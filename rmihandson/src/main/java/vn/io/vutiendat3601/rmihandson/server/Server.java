package vn.io.vutiendat3601.rmihandson.server;

import java.rmi.registry.LocateRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
  public static void main(String[] args) throws Exception {
    var calculator = new CalculatorImpl();
    var registry = LocateRegistry.createRegistry(1099);
    registry.rebind("CalculatorService", calculator);

    log.info("Server started...");
  }
}
