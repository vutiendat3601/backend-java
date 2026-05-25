package vn.io.vutiendat3601.distributedlocking.mapper;

import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.distributedlocking.dto.TransactionDto;
import vn.io.vutiendat3601.distributedlocking.entity.Transaction;

@Component
public class TransactionMapper {
  public TransactionDto mapToTransactionDto(Transaction transaction) {
    return new TransactionDto(
        transaction.getId(),
        transaction.getIdempotentKey(),
        transaction.getUserId(),
        transaction.getAmount(),
        transaction.getBalanceBeforeTransaction(),
        transaction.getBalanceAfterTransaction(),
        transaction.getCreatedAt());
  }
}
