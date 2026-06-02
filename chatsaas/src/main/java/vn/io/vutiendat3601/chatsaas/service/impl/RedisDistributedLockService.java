package vn.io.vutiendat3601.chatsaas.service.impl;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.chatsaas.service.DistributedLockService;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisDistributedLockService implements DistributedLockService {
  private static final int LOCK_TIME_IN_MINUTE = 3;
  private static final String LOCK_VALUE = "1";
  
  private RedisTemplate<String, String> redisTemplate;

  @Override
  public String generateLockKey(String idempotentKey) {
    return "lock:%s".formatted(idempotentKey);
  }

  @Override
  public boolean accquireLock(String idempotentKey) {
    var lockKey = generateLockKey(idempotentKey);
    return redisTemplate
        .opsForValue()
        .setIfAbsent(lockKey, LOCK_VALUE, Duration.ofMinutes(LOCK_TIME_IN_MINUTE));
  }

  @Override
  public void releaseLock(String idempotentKey) {
    var lockKey = generateLockKey(idempotentKey);
    redisTemplate.delete(lockKey);
  }
}
