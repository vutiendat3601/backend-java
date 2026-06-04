package vn.io.vutiendat3601.instamini.dto.request.profile;

import org.hibernate.validator.constraints.Length;

public record UpdateProfileRequest(
    @Length(min = 1, max = 100) String displayName,
    @Length(min = 1, max = 100) String username,
    @Length(min = 1, max = 100) String bio) {}
