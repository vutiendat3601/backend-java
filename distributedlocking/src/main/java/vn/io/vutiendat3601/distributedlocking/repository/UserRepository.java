package vn.io.vutiendat3601.distributedlocking.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import vn.io.vutiendat3601.distributedlocking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<User> findOneWithLockingById(Long id);
}
