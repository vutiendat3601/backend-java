package vn.io.vutiendat3601.imdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.io.vutiendat3601.imdb.dto.FavoriteMovieRequest;
import vn.io.vutiendat3601.imdb.dto.LoginRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationResponse;
import vn.io.vutiendat3601.imdb.dto.UserFavoriteMovieResponse;
import vn.io.vutiendat3601.imdb.exception.ConflictResourceException;
import vn.io.vutiendat3601.imdb.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.imdb.model.User;
import vn.io.vutiendat3601.imdb.model.UserFavoriteMovie;
import vn.io.vutiendat3601.imdb.repository.MovieRepository;
import vn.io.vutiendat3601.imdb.repository.UserFavoriteMovieRepository;
import vn.io.vutiendat3601.imdb.repository.UserRepository;
import vn.io.vutiendat3601.imdb.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final MovieRepository movieRepository;
  private final UserFavoriteMovieRepository userFavoriteMovieRepository;

  @Override
  public UserFavoriteMovieResponse getUserFavoriteMovies(User user) {
    var movieIds = userFavoriteMovieRepository.findUserFavoriteMovieIds(user.id());
    var movies = movieRepository.findByIds(movieIds);
    return new UserFavoriteMovieResponse(user.id(), movies);
  }

  @Override
  public User login(LoginRequest loginReq) {
    var username = loginReq.username();
    var user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(() -> new BadCredentialsException("User not found."));
    if (!passwordEncoder.matches(loginReq.password(), user.hashedPassword())) {
      throw new BadCredentialsException("User not found.");
    }
    return user;
  }

  @Override
  public RegistrationResponse createUser(RegistrationRequest registrationReq) {
    var username = registrationReq.email();
    var hashedPassword = hashPassword(registrationReq.password());
    var user =
        userRepository
            .save(username, hashedPassword)
            .orElseThrow(
                () ->
                    new ConflictResourceException(
                        "The user with username '%s' was taken.".formatted(username)));
    return new RegistrationResponse(user.id());
  }

  @Override
  public void addFavoriteMovie(User user, FavoriteMovieRequest favoriteMovieReq) {
    var movie =
        movieRepository
            .findById(favoriteMovieReq.id())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Movie with id '%l' not found".formatted(favoriteMovieReq.id())));
    var userFavoriteMovie = new UserFavoriteMovie(user.id(), movie.id());
    userFavoriteMovieRepository.save(userFavoriteMovie);
  }

  private String hashPassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
