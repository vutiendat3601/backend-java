package vn.io.vutiendat3601.imdb.dto;

import jakarta.validation.constraints.NotNull;

public record FavoriteMovieRequest(@NotNull Long id) {}
