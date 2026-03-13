package vn.io.vutiendat3601.pagination.mapper;

import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.pagination.dto.StudentDto;
import vn.io.vutiendat3601.pagination.entity.Student;

@Component
public class StudentMapper {
  public StudentDto mapToStudentDto(Student student) {
    return StudentDto.builder()
        .studentId(student.getStudentId())
        .firstName(student.getFirstName())
        .lastName(student.getLastName())
        .createdAt(student.getCreatedAt())
        .build();
  }
}
