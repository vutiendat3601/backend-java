package vn.io.vutiendat3601.distributedlocking.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.distributedlocking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByUserId(Long userId);

  Optional<Transaction> findOneByIdempotentKey(String idempotentKey);
}
