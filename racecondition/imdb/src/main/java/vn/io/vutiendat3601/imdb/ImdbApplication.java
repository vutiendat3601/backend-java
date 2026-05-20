package vn.io.vutiendat3601.imdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.imdb.dto.FavoriteMovieRequest;
import vn.io.vutiendat3601.imdb.dto.LoginRequest;
import vn.io.vutiendat3601.imdb.dto.RegistrationRequest;
import vn.io.vutiendat3601.imdb.repository.MovieRepository;
import vn.io.vutiendat3601.imdb.service.UserService;

@SpringBootApplication
public class ImdbApplication {

  public static void main(String[] args) {
    SpringApplication.run(ImdbApplication.class, args);
  }

  @EventListener
  void onStartup(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var movieRepository = ctx.getBean(MovieRepository.class);
    var userService = ctx.getBean(UserService.class);
    var email = "vutien.dat.3601@gmail.com";
    var password = "123456Aa@";
    var registrationReq = new RegistrationRequest(email, "123456Aa@");
    var loginReq = new LoginRequest(email, password);

    movieRepository.save("Spider man");
    movieRepository.save("Superman");
    movieRepository.save("Wonder woman");

    userService.createUser(registrationReq);
    var user = userService.login(loginReq);
    userService.addFavoriteMovie(user, new FavoriteMovieRequest(1L));
    userService.addFavoriteMovie(user, new FavoriteMovieRequest(2L));
    userService.addFavoriteMovie(user, new FavoriteMovieRequest(3L));
    var userFavoriteMovies = userService.getUserFavoriteMovies(user);
    System.out.println("userFavoriteMovies=" + userFavoriteMovies);
  }
}
