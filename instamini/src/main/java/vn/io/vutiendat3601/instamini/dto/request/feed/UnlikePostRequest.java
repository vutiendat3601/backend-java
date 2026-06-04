package vn.io.vutiendat3601.instamini.dto.request.feed;

import jakarta.validation.constraints.Positive;

public record UnlikePostRequest(@Positive Long id) {}
