package vn.io.vutiendat3601.instamini.dto;

import java.time.Instant;

public record CommentDto(Long id, String content, Instant createdAt, ProfileDto createdBy) {}
