package vn.io.vutiendat3601.instamini.dto.request.feed;

import org.hibernate.validator.constraints.Length;

public record CreatePostRequest(
    @Length(min = 1) String base64ImageString, @Length(min = 1, max = 2000) String caption) {}
