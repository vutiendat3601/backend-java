package vn.io.vutiendat3601.caching.dto;

import jakarta.validation.constraints.Positive;

public record CategoryArticlesRequest(@Positive Long categoryId) {}
