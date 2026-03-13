package vn.io.vutiendat3601.pagination.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
  private String studentId;

  private String firstName;

  private String lastName;

  private LocalDateTime createdAt;
}
