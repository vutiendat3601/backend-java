package vn.io.vutiendat3601.pagination.service;

import vn.io.vutiendat3601.pagination.dto.PaginationResponse;
import vn.io.vutiendat3601.pagination.dto.StudentDto;

public interface StudentService {
  PaginationResponse<StudentDto> getStudents(int pageIndex, int pageSize);

  PaginationResponse<StudentDto> getStudents(String cursor, int limit);
}
