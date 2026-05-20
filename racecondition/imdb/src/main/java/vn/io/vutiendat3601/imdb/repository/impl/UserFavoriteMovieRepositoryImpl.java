package vn.io.vutiendat3601.imdb.repository.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import vn.io.vutiendat3601.imdb.model.UserFavoriteMovie;
import vn.io.vutiendat3601.imdb.repository.UserFavoriteMovieRepository;

@Repository
public class UserFavoriteMovieRepositoryImpl implements UserFavoriteMovieRepository {
  private static Set<UserFavoriteMovie> userFavoriteMoviesSet = ConcurrentHashMap.newKeySet();

  @Override
  public List<Long> findUserFavoriteMovieIds(long userId) {
    return userFavoriteMoviesSet.stream()
        .filter(ufm -> ufm.userId() == userId)
        .map(ufm -> ufm.movieId())
        .toList();
  }

  @Override
  public UserFavoriteMovie save(UserFavoriteMovie userFavoriteMovie) {
    userFavoriteMoviesSet.add(userFavoriteMovie);
    return userFavoriteMovie;
  }
}
