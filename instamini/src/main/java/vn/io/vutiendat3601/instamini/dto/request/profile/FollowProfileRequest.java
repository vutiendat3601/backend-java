package vn.io.vutiendat3601.instamini.dto.request.profile;

import jakarta.validation.constraints.Positive;

public record FollowProfileRequest(@Positive Long followeeProfileId) {}
