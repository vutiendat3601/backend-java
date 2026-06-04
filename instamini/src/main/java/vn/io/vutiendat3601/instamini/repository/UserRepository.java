package vn.io.vutiendat3601.instamini.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.instamini.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  @EntityGraph(attributePaths = "authorities")
  Optional<User> findByUsername(String username);
}
