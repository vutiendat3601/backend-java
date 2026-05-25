package vn.io.vutiendat3601.distributedlocking.dto;

import jakarta.validation.constraints.Positive;

public record GetUserBalanceRequest(@Positive Long userId) {}
