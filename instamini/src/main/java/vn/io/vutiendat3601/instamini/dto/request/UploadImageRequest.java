package vn.io.vutiendat3601.instamini.dto.request;

import org.hibernate.validator.constraints.Length;

public record UploadImageRequest(@Length(min = 1) String base64ImageString) {}
