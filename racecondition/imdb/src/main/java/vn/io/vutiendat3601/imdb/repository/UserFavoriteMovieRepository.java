package vn.io.vutiendat3601.imdb.repository;

import java.util.List;
import vn.io.vutiendat3601.imdb.model.UserFavoriteMovie;

public interface UserFavoriteMovieRepository {
  List<Long> findUserFavoriteMovieIds(long userId);

  UserFavoriteMovie save(UserFavoriteMovie userFavoriteMovie);
}
