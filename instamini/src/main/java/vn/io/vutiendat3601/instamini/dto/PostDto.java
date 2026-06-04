package vn.io.vutiendat3601.instamini.dto;

import java.time.Instant;
import java.util.List;

public record PostDto(
    Long id,
    String imageUrl,
    String caption,
    Instant createdAt,
    ProfileDto createdBy,
    List<CommentDto> comments,
    List<ProfileDto> likedByUsers) {}
