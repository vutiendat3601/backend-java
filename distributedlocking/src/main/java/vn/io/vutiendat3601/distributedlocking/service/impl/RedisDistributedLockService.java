package vn.io.vutiendat3601.distributedlocking.service.impl;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.distributedlocking.service.DistributedLockService;

@Service
public class RedisDistributedLockService implements DistributedLockService {
  private static final int LOCK_TIME_IN_MINUTE = 3;
  private static final String LOCK_VALUE = "1";
  @Autowired private RedisTemplate<String, String> redisTemplate;

  private String generateLockKey(String idempotentKey) {
    return String.format("lock:%s", idempotentKey);
  }

  @Override
  public boolean accquireLock(String idempotentKey) {
    String lockKey = generateLockKey(idempotentKey);
    return redisTemplate
        .opsForValue()
        .setIfAbsent(lockKey, LOCK_VALUE, Duration.ofMinutes(LOCK_TIME_IN_MINUTE));
  }

  @Override
  public void releaseLock(String idempotentKey) {
    String lockKey = generateLockKey(idempotentKey);
    redisTemplate.delete(lockKey);
  }
}
