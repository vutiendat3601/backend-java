package vn.io.vutiendat3601.instamini.service.profile.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.dto.request.UploadImageRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UpdateProfileImageRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UpdateProfileRequest;
import vn.io.vutiendat3601.instamini.dto.response.profile.GetProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.UpdateProfileImageResponse;
import vn.io.vutiendat3601.instamini.entity.Profile;
import vn.io.vutiendat3601.instamini.exception.ProfileNotFoundException;
import vn.io.vutiendat3601.instamini.mapper.ProfileMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.UploadService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@RequiredArgsConstructor
@Service
public class ProfileServiceV1 implements ProfileService {
  private final ProfileRepository profileRepository;
  private final UploadService uploadService;
  private final ProfileMapper profileMapper;

  @Override
  public GetProfileResponse getProfile(UserPrincipal userPrincipal) {
    var profile = createProfileIfNotExists(userPrincipal);
    var profileDto = profileMapper.mapToProfileDto(profile);
    return new GetProfileResponse(profileDto);
  }

  @Override
  public GetProfileResponse getProfile(Long id) {
    var profile =
        profileRepository
            .findById(id)
            .orElseThrow(
                () -> new ProfileNotFoundException("Profile not found: id=%d".formatted(id)));
    var profileDto = profileMapper.mapToProfileDto(profile);
    return new GetProfileResponse(profileDto);
  }

  @Transactional
  @Override
  public GetProfileResponse updateProfile(
      UserPrincipal userPrincipal, UpdateProfileRequest updateProfileReq) {
    var profile = createProfileIfNotExists(userPrincipal);
    profile.setBio(updateProfileReq.bio());
    profile.setDisplayName(updateProfileReq.displayName());
    profileRepository.save(profile);
    var profileDto = profileMapper.mapToProfileDto(profile);
    return new GetProfileResponse(profileDto);
  }

  @Transactional
  @Override
  public UpdateProfileImageResponse updateProfileImage(
      UserPrincipal userPrincipal, UpdateProfileImageRequest updateProfileImageReq) {
    var filePath =
        uploadService
            .uploadImage(new UploadImageRequest(updateProfileImageReq.base64ImageString()))
            .filePath();
    var profile = createProfileIfNotExists(userPrincipal);
    profile.setProfileImageFilePath(filePath);
    profileRepository.save(profile);
    var profileDto = profileMapper.mapToProfileDto(profile);
    return new UpdateProfileImageResponse(profileDto);
  }

  private Profile createProfileIfNotExists(UserPrincipal userPrincipal) {
    var userId = userPrincipal.getId();
    return profileRepository
        .findByUserId(userId)
        .orElseGet(
            () -> {
              var newProfile =
                  Profile.builder()
                      .userId(userId)
                      .profileImageUrl(userPrincipal.getPictureUrl())
                      .displayName(userPrincipal.getName())
                      .build();
              return profileRepository.save(newProfile);
            });
  }
}
