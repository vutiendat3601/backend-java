package vn.io.vutiendat3601.rmihandson.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import vn.io.vutiendat3601.rmihandson.common.Calculator;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

  protected CalculatorImpl() throws RemoteException {
    super();
  }

  @Override
  public int add(int a, int b) throws RemoteException {
    return a + b;
  }

  @Override
  public int subtract(int a, int b) throws RemoteException {
    return a - b;
  }
}
