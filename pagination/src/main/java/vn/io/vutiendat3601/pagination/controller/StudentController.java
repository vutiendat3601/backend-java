package vn.io.vutiendat3601.pagination.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.pagination.dto.PaginationResponse;
import vn.io.vutiendat3601.pagination.dto.StudentDto;
import vn.io.vutiendat3601.pagination.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/students")
public class StudentController {
  private final StudentService studentService;

  @GetMapping("list-offset")
  public ResponseEntity<PaginationResponse<StudentDto>> getStudents(
      @PositiveOrZero @RequestParam Integer pageIndex, @Positive @RequestParam Integer pageSize) {
    return ResponseEntity.ok(studentService.getStudents(pageIndex, pageSize));
  }

  @GetMapping("list-cursor")
  public ResponseEntity<PaginationResponse<StudentDto>> getStudents(
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false, defaultValue = "50") Integer pageSize) {
    return ResponseEntity.ok(studentService.getStudents(cursor, pageSize));
  }
}
