package vn.io.vutiendat3601.distributedlocking.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionRequest;
import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionResponse;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsResponse;
import vn.io.vutiendat3601.distributedlocking.entity.Transaction;
import vn.io.vutiendat3601.distributedlocking.exception.DuplicatedIdempotentKeyException;
import vn.io.vutiendat3601.distributedlocking.exception.NotEnoughBalanceException;
import vn.io.vutiendat3601.distributedlocking.exception.TooManyRequestException;
import vn.io.vutiendat3601.distributedlocking.exception.UserNotFoundException;
import vn.io.vutiendat3601.distributedlocking.mapper.TransactionMapper;
import vn.io.vutiendat3601.distributedlocking.repository.TransactionRepository;
import vn.io.vutiendat3601.distributedlocking.repository.UserRepository;
import vn.io.vutiendat3601.distributedlocking.service.DistributedLockService;
import vn.io.vutiendat3601.distributedlocking.service.TransactionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceV1 implements TransactionService {
  private final DistributedLockService distributedLockService;
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;
  private final TransactionMapper transactionMapper;

  @Transactional
  @Override
  public CreateTransactionResponse createTransaction(
      CreateTransactionRequest createTransactionReq) {
    var idempotentKey = createTransactionReq.idempotentKey();
    var userId = createTransactionReq.userId();
    var accquiredLock = distributedLockService.accquireLock(idempotentKey);
    if (!accquiredLock) {
      log.info("Cannot accquired lock.");
      throw new TooManyRequestException("");
    }
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("Accquired lock succesfully.");
    try {
      var existedTransactionOpt = transactionRepository.findOneByIdempotentKey(idempotentKey);
      log.info("existedTransactionOpt={}", existedTransactionOpt);
      if (existedTransactionOpt.isPresent()) {
        var existedTransaction = existedTransactionOpt.get();
        var isValid =
            existedTransaction.getUserId().equals(userId)
                && existedTransaction.getAmount().equals(createTransactionReq.amount());
        if (isValid) {
          log.info("Transaction proceessed before: {}", existedTransaction);
          return new CreateTransactionResponse(
              existedTransaction.getId(), existedTransaction.getBalanceAfterTransaction());
        } else {
          throw new DuplicatedIdempotentKeyException("Invalid request.");
        }
      } else {
        var user =
            userRepository
                .findOneWithLockingById(userId)
                .orElseThrow(
                    () ->
                        new UserNotFoundException(
                            "User with id '%d' not found.".formatted(userId)));
        if (user.getBalance() < createTransactionReq.amount()) {
          throw new NotEnoughBalanceException("User balance is not enough.");
        }
        var balanceBeforeTransaction = user.getBalance();
        var balanceAfterTransaction = user.getBalance() - createTransactionReq.amount();
        user.setBalance(balanceAfterTransaction);
        userRepository.save(user);
        var transaction =
            Transaction.builder()
                .idempotentKey(idempotentKey)
                .userId(userId)
                .amount(createTransactionReq.amount())
                .balanceBeforeTransaction(balanceBeforeTransaction)
                .balanceAfterTransaction(balanceAfterTransaction)
                .build();
        transaction = transactionRepository.save(transaction);
        return new CreateTransactionResponse(transaction.getId(), balanceAfterTransaction);
      }
    } finally {
      distributedLockService.releaseLock(idempotentKey);
      log.info("Released lock succesfully: idempotentKey=%s".formatted(idempotentKey));
    }
  }

  @Override
  public GetUserTransactionsResponse getUserTransactions(
      GetUserTransactionsRequest getUserTransactionsReq) {
    var transactions = transactionRepository.findByUserId(getUserTransactionsReq.userId());
    var transactionDtos =
        transactions.stream().map(transactionMapper::mapToTransactionDto).toList();
    return new GetUserTransactionsResponse(transactionDtos);
  }
}
