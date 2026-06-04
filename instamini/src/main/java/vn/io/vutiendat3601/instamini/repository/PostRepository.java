package vn.io.vutiendat3601.instamini.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.io.vutiendat3601.instamini.entity.Post;
import vn.io.vutiendat3601.instamini.entity.Profile;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByCreatedBy(Profile createdBy);

  @Query(
      value =
"""
SELECT * FROM post WHERE created_by_id IN :ids
ORDER BY created_at DESC LIMIT :limit OFFSET :offset
""",
      nativeQuery = true)
  List<Post> findByCreatedByIdIn(
      @Param("ids") List<Long> createdByIds,
      @Param(value = "limit") long limit,
      @Param(value = "offset") long offset);

  @Query(
      value =
"""
SELECT count(*) FROM post WHERE created_by_id IN :ids
""",
      nativeQuery = true)
  long countByCreatedByIdIn(@Param("ids") List<Long> createdByIds);
}
