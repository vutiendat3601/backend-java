package vn.io.vutiendat3601.chatsaas.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.io.vutiendat3601.chatsaas.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByAppIdAndClientUserId(UUID appId, String clientUserId);
}
