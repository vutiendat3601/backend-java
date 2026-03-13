package vn.io.vutiendat3601.pagination.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse<T> {
  private Collection<T> items;

  private int pageIndex;

  private int pageSize;

  private int totalPages;

  private long totalItems;

  private String cursor;

  public static <K> PaginationResponse<K> of(Page<K> page) {
    return PaginationResponse.<K>builder()
        .items(page.getContent())
        .pageIndex(page.getNumber())
        .pageSize(page.getSize())
        .totalPages(page.getTotalPages())
        .totalItems(page.getTotalElements())
        .build();
  }
}
