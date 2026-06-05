package vn.io.vutiendat3601.rankingsystem.entity;

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
public class Ranking {
  private String name;

  @Builder.Default private Double score = 0D;
}
