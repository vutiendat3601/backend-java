package vn.io.vutiendat3601.distributedlocking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "idempotent_key", unique = true, nullable = false, length = 200)
  private String idempotentKey;

  @Column(name = "userId", nullable = false)
  private Long userId;

  @Column(name = "amount", nullable = false)
  private Long amount;

  @Column(name = "balance_before_transaction", nullable = false)
  private Long balanceBeforeTransaction;

  @Column(name = "balance_after_transaction", nullable = false)
  private Long balanceAfterTransaction;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
