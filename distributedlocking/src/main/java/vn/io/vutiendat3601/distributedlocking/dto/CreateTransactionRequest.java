package vn.io.vutiendat3601.distributedlocking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CreateTransactionRequest(
    @Positive @NotNull Long userId,
    @Positive @NotNull Long amount,
    @Length(max = 100, min = 10) @NotNull String idempotentKey) {}
