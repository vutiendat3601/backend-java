package vn.io.vutiendat3601.imdb.dto;

import java.util.List;
import vn.io.vutiendat3601.imdb.model.Movie;

public record UserFavoriteMovieResponse(long userId, List<Movie> movies) {}
