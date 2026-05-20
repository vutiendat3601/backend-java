package vn.io.vutiendat3601.imdb.repository.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import vn.io.vutiendat3601.imdb.model.User;
import vn.io.vutiendat3601.imdb.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private AtomicLong idGenerator = new AtomicLong(0L);
  private final Map<String, User> usernameUserMap = new ConcurrentHashMap<>();

  @Override
  public Optional<User> save(String username, String hashedPassword) {
    var user = new User(idGenerator.incrementAndGet(), username, hashedPassword);
    var previousUser = usernameUserMap.putIfAbsent(username, user);
    if (Objects.isNull(previousUser)) {
      return Optional.of(user);
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(usernameUserMap.get(username));
  }
}
