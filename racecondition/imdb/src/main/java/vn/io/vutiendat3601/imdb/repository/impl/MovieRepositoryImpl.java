package vn.io.vutiendat3601.imdb.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import vn.io.vutiendat3601.imdb.model.Movie;
import vn.io.vutiendat3601.imdb.repository.MovieRepository;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
  private final AtomicLong idGenerator = new AtomicLong(0);
  private final Map<Long, Movie> idMovieMap = new ConcurrentHashMap<>();

  @Override
  public Movie save(String title) {
    long id = idGenerator.incrementAndGet();
    var movie = new Movie(id, title);
    idMovieMap.put(id, movie);
    return movie;
  }

  @Override
  public Optional<Movie> findById(long id) {
    return Optional.ofNullable(idMovieMap.get(id));
  }

  @Override
  public List<Movie> findByIds(List<Long> movieIds) {
    return movieIds.stream().map(idMovieMap::get).filter(Objects::nonNull).toList();
  }
}
