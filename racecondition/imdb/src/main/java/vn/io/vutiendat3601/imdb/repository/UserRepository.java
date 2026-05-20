package vn.io.vutiendat3601.imdb.repository;

import java.util.Optional;
import vn.io.vutiendat3601.imdb.model.User;

public interface UserRepository {
  Optional<User> save(String email, String hashedPassword);

  Optional<User> findUserByUsername(String username);
}
