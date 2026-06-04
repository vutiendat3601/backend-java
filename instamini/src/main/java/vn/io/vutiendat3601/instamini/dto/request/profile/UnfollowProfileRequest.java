package vn.io.vutiendat3601.instamini.dto.request.profile;

import jakarta.validation.constraints.Positive;

public record UnfollowProfileRequest(@Positive Long followeeProfileId) {}
