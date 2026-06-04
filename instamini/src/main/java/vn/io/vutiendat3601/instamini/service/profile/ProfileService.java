package vn.io.vutiendat3601.instamini.service.profile;

import vn.io.vutiendat3601.instamini.dto.request.profile.UpdateProfileImageRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UpdateProfileRequest;
import vn.io.vutiendat3601.instamini.dto.response.profile.GetProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.UpdateProfileImageResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface ProfileService {
  GetProfileResponse getProfile(UserPrincipal userPrincipal);

  GetProfileResponse getProfile(Long id);

  GetProfileResponse updateProfile(
      UserPrincipal userPrincipal, UpdateProfileRequest updateProfileReq);

  UpdateProfileImageResponse updateProfileImage(
      UserPrincipal userPrincipal, UpdateProfileImageRequest updateProfileImageReq);
}
