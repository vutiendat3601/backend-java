package vn.io.vutiendat3601.rmihandson.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
  int add(int a, int b) throws RemoteException;

  int subtract(int a, int b) throws RemoteException;
}
