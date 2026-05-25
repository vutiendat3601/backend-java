package vn.io.vutiendat3601.distributedlocking.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionRequest;
import vn.io.vutiendat3601.distributedlocking.dto.CreateTransactionResponse;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsRequest;
import vn.io.vutiendat3601.distributedlocking.dto.GetUserTransactionsResponse;
import vn.io.vutiendat3601.distributedlocking.service.TransactionService;

@RestController
@Timed(histogram = true)
@RequestMapping("v1/transactions")
public class TransactionController {
  private final Counter counter;
  private final TransactionService transactionService;

  public TransactionController(MeterRegistry registry, TransactionService transactionService) {
    this.counter =
        Counter.builder("my_custom_counter")
            .description("A custom counter metric")
            .register(registry);
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<CreateTransactionResponse> createTransaction(
      @Valid @RequestBody CreateTransactionRequest createTransactionReq) {
    var createTransactionResponse = transactionService.createTransaction(createTransactionReq);
    return ResponseEntity.ok(createTransactionResponse);
  }

  @GetMapping("{userId}")
  public ResponseEntity<GetUserTransactionsResponse> getUserTransactions(
      @PathVariable Long userId) {
    counter.increment();
    var getUserTransactionsResponse =
        transactionService.getUserTransactions(new GetUserTransactionsRequest(userId));
    return ResponseEntity.ok(getUserTransactionsResponse);
  }
}
