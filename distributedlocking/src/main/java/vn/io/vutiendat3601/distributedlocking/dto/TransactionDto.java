package vn.io.vutiendat3601.distributedlocking.dto;

import java.time.Instant;

public record TransactionDto(
    Long id,
    String idempotentKey,
    Long userId,
    Long amount,
    Long balanceBeforeTransaction,
    long balanceAfterTransaction,
    Instant createdAt) {}
