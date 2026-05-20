package vn.io.vutiendat3601.pagination;

import java.time.Instant;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import vn.io.vutiendat3601.pagination.entity.Student;
import vn.io.vutiendat3601.pagination.repository.StudentRepository;

@RequiredArgsConstructor
@Component
public class Seeder {
  private final StudentRepository studentRepository;

  public void setUp() {
    for (int i = 0; i < 100; i++) {
      var FAKER = new Faker();
      var createdAtInstant =
          FAKER
              .timeAndDate()
              .between(Instant.ofEpochSecond(1356998400L), Instant.ofEpochSecond(1767225600L));
      var student =
          Student.builder()
              .firstName(FAKER.name().firstName())
              .lastName(FAKER.name().lastName())
              .studentId("CT%06d".formatted(i + 1))
              .createdAt(createdAtInstant)
              .build();
      studentRepository.save(student);
    }
  }

  public void tearDown() {
    var ids = new LinkedList<Long>();
    for (long i = 0; i < 100_000; i++) {
      ids.add(i);
    }
    studentRepository.deleteAllById(ids);
  }
}
