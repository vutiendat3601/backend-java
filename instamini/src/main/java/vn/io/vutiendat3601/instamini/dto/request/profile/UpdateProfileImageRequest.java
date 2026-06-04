package vn.io.vutiendat3601.instamini.dto.request.profile;

import org.hibernate.validator.constraints.Length;

public record UpdateProfileImageRequest(@Length(min = 1) String base64ImageString) {}
