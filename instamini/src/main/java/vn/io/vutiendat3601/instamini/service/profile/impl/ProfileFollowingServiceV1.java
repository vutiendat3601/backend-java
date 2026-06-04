package vn.io.vutiendat3601.instamini.service.profile.impl;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.dto.request.profile.FollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFolloweeProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFollowerProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UnfollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.response.profile.FollowProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFolloweeProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFollowerProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.UnfollowProfileResponse;
import vn.io.vutiendat3601.instamini.entity.ProfileFollowing;
import vn.io.vutiendat3601.instamini.exception.InvalidInputException;
import vn.io.vutiendat3601.instamini.mapper.ProfileMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.ProfileFollowingRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.profile.ProfileFollowingService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileFollowingServiceV1 implements ProfileFollowingService {
  private final ProfileMapper profileMapper;

  private final ProfileRepository profileRepository;

  private final ProfileService profileService;

  private final ProfileFollowingRepository profileFollowingRepository;

  @Override
  public FollowProfileResponse follow(
      UserPrincipal userPrincipal, FollowProfileRequest followProfileReq) {
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    if (userPrincipalProfileDto.id().equals(followProfileReq.followeeProfileId())) {
      throw new InvalidInputException("Cannot follow yourself.");
    }
    var profileFollowingOpt =
        profileFollowingRepository.findByFollowerProfileIdAndFolloweeProfileId(
            userPrincipalProfileDto.id(), followProfileReq.followeeProfileId());
    if (profileFollowingOpt.isPresent()) {
      return new FollowProfileResponse();
    }
    var profileFollowing = new ProfileFollowing();
    profileFollowing.setFollowerProfileId(userPrincipalProfileDto.id());
    profileFollowing.setFolloweeProfileId(followProfileReq.followeeProfileId());
    profileFollowing.setCreatedAt(Instant.now());
    profileFollowingRepository.save(profileFollowing);
    return new FollowProfileResponse();
  }

  @Override
  public UnfollowProfileResponse unfollow(
      UserPrincipal userPrincipal, UnfollowProfileRequest unfollowProfileReq) {
    var profileDto = profileService.getProfile(userPrincipal).profile();
    profileFollowingRepository
        .findByFollowerProfileIdAndFolloweeProfileId(
            profileDto.id(), unfollowProfileReq.followeeProfileId())
        .ifPresent(
            profileFollowing ->
                profileFollowingRepository.deleteByFollowerProfileIdAndFolloweeProfileId(
                    profileFollowing.getFollowerProfileId(),
                    unfollowProfileReq.followeeProfileId()));
    return new UnfollowProfileResponse();
  }

  @Override
  public ListFollowerProfileResponse listFollowers(
      ListFollowerProfileRequest listFollowerProfileReq, Long page, Long limit) {
    // Validate followeeProfileId existence.
    profileService.getProfile(listFollowerProfileReq.followeeProfileId()).profile();

    var totalFollowers =
        profileFollowingRepository.countByFolloweeProfileId(
            listFollowerProfileReq.followeeProfileId());
    log.info(
        "followeeProfileId={}, totalFollower={}",
        listFollowerProfileReq.followeeProfileId(),
        totalFollowers);
    var totalPage = (long) Math.ceil((double) totalFollowers / limit);
    var offset = (page - 1) * limit;
    var profileFollowings =
        profileFollowingRepository.findByFolloweeProfileId(
            listFollowerProfileReq.followeeProfileId(), limit, offset);
    var followerProfileDtos =
        profileFollowings.stream()
            .map(
                profileFollowing ->
                    profileRepository
                        .findById(profileFollowing.getFollowerProfileId())
                        .orElse(null))
            .filter(Objects::nonNull)
            .map(profileMapper::mapToProfileDto)
            .toList();
    return new ListFollowerProfileResponse(
        followerProfileDtos, listFollowerProfileReq.followeeProfileId(), totalPage);
  }

  @Override
  public ListFolloweeProfileResponse listFollowees(
      ListFolloweeProfileRequest listFolloweeProfileReq, Long page, Long limit) {
    // Validate followerProfileId
    profileService.getProfile(listFolloweeProfileReq.followerProfileId());

    var totalFollowees =
        profileFollowingRepository.countByFollowerProfileId(
            listFolloweeProfileReq.followerProfileId());
    log.info(
        "followerProfileId={}, totalFollowee={}",
        listFolloweeProfileReq.followerProfileId(),
        totalFollowees);
    var totalPage = (long) Math.ceil((double) totalFollowees / limit);
    var offset = (page - 1) * limit;
    var profileFollowings =
        profileFollowingRepository.findByFollowerProfileId(
            listFolloweeProfileReq.followerProfileId(), limit, offset);
    var followeeProfileDtos =
        profileFollowings.stream()
            .map(
                profileFollowing ->
                    profileRepository
                        .findById(profileFollowing.getFolloweeProfileId())
                        .orElse(null))
            .filter(Objects::nonNull)
            .map(profileMapper::mapToProfileDto)
            .toList();

    return new ListFolloweeProfileResponse(
        followeeProfileDtos, listFolloweeProfileReq.followerProfileId(), totalPage);
  }
}
