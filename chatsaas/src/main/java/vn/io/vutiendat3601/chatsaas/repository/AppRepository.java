package vn.io.vutiendat3601.chatsaas.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.chatsaas.entity.App;

public interface AppRepository extends JpaRepository<App, UUID> {
  Optional<App> findByApiKey(String apiKey);
}
