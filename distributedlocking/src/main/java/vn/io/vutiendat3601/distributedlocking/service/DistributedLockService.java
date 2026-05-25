package vn.io.vutiendat3601.distributedlocking.service;

public interface DistributedLockService {
  boolean accquireLock(String idempotentKey);
  
  void releaseLock(String idempotentKey);
}
