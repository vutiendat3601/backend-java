package vn.io.vutiendat3601.distributedlocking.service;

import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionRequest;
import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionResponse;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsResponse;

public interface TransactionService {
  CreateTransactionResponse createTransaction(CreateTransactionRequest request);

  GetUserTransactionsResponse getUserTransactions(GetUserTransactionsRequest request);
}
