package vn.io.vutiendat3601.relationaldatabaselocking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "room")
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Builder.Default private Boolean available = true;

  @Version private long version;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

  @Builder.Default private LocalDateTime updatedAt = LocalDateTime.now(ZoneOffset.UTC);
}
