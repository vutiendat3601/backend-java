package vn.io.vutiendat3601.imdb.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.imdb.dto.FavoriteMovieRequest;
import vn.io.vutiendat3601.imdb.dto.UserFavoriteMovieResponse;
import vn.io.vutiendat3601.imdb.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/user")
public class FavoriteMovieController extends AbstractController {
  private final UserService userService;

  @GetMapping("favorite-movies")
  public ResponseEntity<UserFavoriteMovieResponse> get(HttpServletRequest req) {
    var user = getUserFromSession(req);
    if (Objects.isNull(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    log.info("Get favorite movide for user {%d}".formatted(user.id()));
    var resp = userService.getUserFavoriteMovies(user);
    return ResponseEntity.ok(resp);
  }

  @PostMapping("favorite-movies")
  public ResponseEntity<UserFavoriteMovieResponse> addFavoriteMovie(
      @RequestBody FavoriteMovieRequest favoriteMovieReq, HttpServletRequest req) {
    var user = getUserFromSession(req);
    if (Objects.isNull(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    log.info("Add favorite movide for user {%d}".formatted(user.id()));
    userService.addFavoriteMovie(user, favoriteMovieReq);
    return ResponseEntity.ok().build();
  }
}
