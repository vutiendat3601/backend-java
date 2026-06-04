package vn.io.vutiendat3601.instamini.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
@Entity
public class Authority {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "authority", nullable = false)
  public String authority;
}
