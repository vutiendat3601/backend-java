package vn.io.vutiendat3601.relationaldatabaselocking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "booking")
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long roomId;

  private Long userId;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

  @Builder.Default private LocalDateTime updatedAt = LocalDateTime.now(ZoneOffset.UTC);
}
