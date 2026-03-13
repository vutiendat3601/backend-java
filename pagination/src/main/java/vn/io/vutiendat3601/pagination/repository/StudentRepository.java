package vn.io.vutiendat3601.pagination.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.io.vutiendat3601.pagination.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

  @Query(
"""
SELECT s FROM student s WHERE s.createdAt < :createdAt ORDER BY s.createdAt DESC LIMIT :limit
""")
  List<Student> findByCreatedAtCursor(
      @Param("createdAt") LocalDateTime createdAt, @Param("limit") int limit);
}
