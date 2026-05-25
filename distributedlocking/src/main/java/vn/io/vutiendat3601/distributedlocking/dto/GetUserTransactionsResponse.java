package vn.io.vutiendat3601.distributedlocking.dto;

import java.util.List;


public record GetUserTransactionsResponse(List<TransactionDto> transactions) {}
