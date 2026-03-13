package vn.io.vutiendat3601.pagination.service.impl;

import static vn.io.vutiendat3601.pagination.constant.Constant.LOCAL_DATE_TIME_MAX;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.pagination.dto.PaginationResponse;
import vn.io.vutiendat3601.pagination.dto.StudentDto;
import vn.io.vutiendat3601.pagination.entity.Student;
import vn.io.vutiendat3601.pagination.mapper.StudentMapper;
import vn.io.vutiendat3601.pagination.repository.StudentRepository;
import vn.io.vutiendat3601.pagination.service.StudentService;

@RequiredArgsConstructor
@Service
public class StudentServiceV1 implements StudentService {
  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  private final Base64.Encoder base64Encoder;
  private final Base64.Decoder base64Decoder;

  @Override
  public PaginationResponse<StudentDto> getStudents(int pageIndex, int pageSize) {
    Page<Student> page = studentRepository.findAll(PageRequest.of(pageIndex, pageSize));
    return PaginationResponse.of(page.map(studentMapper::mapToStudentDto));
  }

  @Override
  public PaginationResponse<StudentDto> getStudents(String cursor, int pageSize) {

    LocalDateTime createdAtCursor = LOCAL_DATE_TIME_MAX;
    if (Objects.nonNull(cursor)) {
      final String decodedCursor = new String(base64Decoder.decode(cursor.getBytes()));
      createdAtCursor = LocalDateTime.parse(decodedCursor);
    }

    final List<Student> students =
        studentRepository.findByCreatedAtCursor(createdAtCursor, pageSize);
    LocalDateTime cursorDateTime = LOCAL_DATE_TIME_MAX;
    if (students.size() > 0) {
      cursorDateTime = students.get(students.size() - 1).getCreatedAt();
    }
    final String newCursor =
        Optional.ofNullable(cursorDateTime).orElse(LOCAL_DATE_TIME_MAX).toString();
    final String encodedNewCursor = new String(base64Encoder.encode(newCursor.getBytes()));
    final List<StudentDto> studentDtos =
        students.stream().map(studentMapper::mapToStudentDto).toList();
    return PaginationResponse.<StudentDto>builder()
        .items(studentDtos)
        .pageIndex(-1)
        .totalItems(-1)
        .totalPages(-1)
        .pageSize(pageSize)
        .cursor(encodedNewCursor)
        .build();
  }
}
