package vn.io.vutiendat3601.imdb.service;

import vn.io.vutiendat3601.imdb.dto.FavoriteMovieRequest;
import vn.io.vutiendat3601.imdb.dto.LoginRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationResponse;
import vn.io.vutiendat3601.imdb.dto.UserFavoriteMovieResponse;
import vn.io.vutiendat3601.imdb.model.User;

public interface UserService {
  UserFavoriteMovieResponse getUserFavoriteMovies(User user);

  void addFavoriteMovie(User user, FavoriteMovieRequest favoriteMovieReq);

  User login(LoginRequest loginReq);

  RegistrationResponse createUser(RegistrationRequest registrationReq);
}
