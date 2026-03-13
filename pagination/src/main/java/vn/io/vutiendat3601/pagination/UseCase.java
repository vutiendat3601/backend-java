package vn.io.vutiendat3601.pagination;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.pagination.entity.Student;
import vn.io.vutiendat3601.pagination.repository.StudentRepository;

@RequiredArgsConstructor
@Component
public class UseCase {
  private final StudentRepository studentRepository;

  public void setUp() {
    for (int i = 1; i <= 100; i++) {
      final Faker FAKER = new Faker();
      final Instant createdAtInstant =
          FAKER
              .timeAndDate()
              .between(Instant.ofEpochSecond(1356998400L), Instant.ofEpochSecond(1767225600L));
      final Student student =
          Student.builder()
              .firstName(FAKER.name().firstName())
              .lastName(FAKER.name().lastName())
              .studentId("CT%06d".formatted(i))
              .createdAt(LocalDateTime.ofInstant(createdAtInstant, ZoneOffset.UTC))
              .build();
      studentRepository.save(student);
    }
  }

  public void tearDown() {
    final List<Long> ids = new LinkedList<>();
    for (int i = 0; i < 100_000; i++) {
      ids.add((long) i);
    }
    studentRepository.deleteAllById(ids);
  }

  public void tryCase(int caseNumber) {
    setUp();

    // tearDown();
  }
}
