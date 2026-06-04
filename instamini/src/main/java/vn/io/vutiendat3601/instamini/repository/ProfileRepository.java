package vn.io.vutiendat3601.instamini.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.instamini.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
  Optional<Profile> findByUserId(UUID userId);
}
