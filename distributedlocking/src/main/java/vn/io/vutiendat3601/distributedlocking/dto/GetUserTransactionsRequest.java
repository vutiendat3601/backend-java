package vn.io.vutiendat3601.distributedlocking.dto;

import jakarta.validation.constraints.Positive;

public record GetUserTransactionsRequest(@Positive Long userId) {}
