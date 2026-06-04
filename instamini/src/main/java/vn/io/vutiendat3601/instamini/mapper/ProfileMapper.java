package vn.io.vutiendat3601.instamini.mapper;

import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.instamini.dto.ProfileDto;
import vn.io.vutiendat3601.instamini.entity.Profile;

@Component
public class ProfileMapper {
  public ProfileDto mapToProfileDto(Profile profile) {
    return new ProfileDto(
        profile.getId(),
        profile.getUserId(),
        profile.getProfileImageFilePath(),
        profile.getDisplayName(),
        profile.getBio());
  }
}
