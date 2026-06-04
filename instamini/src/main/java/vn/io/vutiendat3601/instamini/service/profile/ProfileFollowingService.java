package vn.io.vutiendat3601.instamini.service.profile;

import vn.io.vutiendat3601.instamini.dto.request.profile.FollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFolloweeProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFollowerProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UnfollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.response.profile.FollowProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFolloweeProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFollowerProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.UnfollowProfileResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface ProfileFollowingService {
  FollowProfileResponse follow(UserPrincipal userPrincipal, FollowProfileRequest followProfileReq);

  UnfollowProfileResponse unfollow(
      UserPrincipal userPrincipal, UnfollowProfileRequest unfollowProfileReq);

  ListFollowerProfileResponse listFollowers(
      ListFollowerProfileRequest listFollowerProfileReq, Long page, Long limit);

  ListFolloweeProfileResponse listFollowees(
      ListFolloweeProfileRequest listFolloweeProfileReq, Long page, Long limit);
}
