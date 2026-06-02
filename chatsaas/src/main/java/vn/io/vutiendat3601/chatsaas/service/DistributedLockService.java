package vn.io.vutiendat3601.chatsaas.service;

public interface DistributedLockService {
  String generateLockKey(String idempotentKey);

  boolean accquireLock(String idempotentKey);

  void releaseLock(String idempotentKey);
}
