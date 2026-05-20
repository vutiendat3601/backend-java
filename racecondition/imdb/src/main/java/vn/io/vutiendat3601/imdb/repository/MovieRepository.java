package vn.io.vutiendat3601.imdb.repository;

import java.util.List;
import java.util.Optional;
import vn.io.vutiendat3601.imdb.model.Movie;

public interface MovieRepository {
  Movie save(String title);

  Optional<Movie> findById(long id);

  List<Movie> findByIds(List<Long> movieIds);
}
