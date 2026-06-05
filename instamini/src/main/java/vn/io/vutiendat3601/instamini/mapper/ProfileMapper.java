package vn.io.vutiendat3601.instamini.mapper;

import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_BASE_URL;
import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.instamini.dto.ProfileDto;
import vn.io.vutiendat3601.instamini.entity.Profile;

@RequiredArgsConstructor
@Component
public class ProfileMapper {
  private final Environment env;

  public ProfileDto mapToProfileDto(Profile profile) {
    var profileImageUrl = profile.getProfileImageUrl();
    if (Objects.nonNull(profile.getProfileImageFilePath())) {
      var minioBaseUrl = env.getProperty("minio.baseUrl", MINIO_DEFAULT_BASE_URL);
      var minioPublicImageBucket =
          env.getProperty("minio.public-image-bucket", MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET);
      profileImageUrl =
          minioBaseUrl + "/" + minioPublicImageBucket + "/" + profile.getProfileImageFilePath();
    }
    return new ProfileDto(
        profile.getId(),
        profile.getUserId(),
        profileImageUrl,
        profile.getDisplayName(),
        profile.getBio());
  }
}
