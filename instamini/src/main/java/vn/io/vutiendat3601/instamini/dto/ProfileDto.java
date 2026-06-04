package vn.io.vutiendat3601.instamini.dto;

import java.util.UUID;

public record ProfileDto(
    Long id, UUID userId, String profileImageUrl, String displayName, String bio) {}
