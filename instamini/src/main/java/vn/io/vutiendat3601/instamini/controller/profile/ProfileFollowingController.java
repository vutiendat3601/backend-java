package vn.io.vutiendat3601.instamini.controller.profile;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.request.profile.FollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFolloweeProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.ListFollowerProfileRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.UnfollowProfileRequest;
import vn.io.vutiendat3601.instamini.dto.response.profile.FollowProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFolloweeProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.ListFollowerProfileResponse;
import vn.io.vutiendat3601.instamini.dto.response.profile.UnfollowProfileResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.service.profile.ProfileFollowingService;

@Tag(name = "Following")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/following")
@RestController
public class ProfileFollowingController {
  private final ProfileFollowingService profileFollowingService;

  @GetMapping("profiles/{id}/followers")
  public ResponseEntity<ListFollowerProfileResponse> listFollowers(
      @PathVariable(name = "id") Long id,
      @RequestParam(name = "page", defaultValue = "1") @Min(1) Long page,
      @RequestParam(name = "limit", defaultValue = "10") @Min(1) Long limit) {
    log.info("followeeProfileId={}, page={}, limit={}", id, page, limit);
    var listFollowerProfileResp =
        profileFollowingService.listFollowers(new ListFollowerProfileRequest(id), page, limit);
    return ResponseEntity.ok(listFollowerProfileResp);
  }

  @GetMapping("profiles/{id}/followees")
  public ResponseEntity<ListFolloweeProfileResponse> listFollowees(
      @PathVariable(name = "id") Long id,
      @RequestParam(name = "page", defaultValue = "1") @Min(1) Long page,
      @RequestParam(name = "limit", defaultValue = "10") @Min(1) Long limit) {
    log.info("followerProfileId={}, page={}, limit={}", id, page, limit);
    var listFolloweeProfileResponse =
        profileFollowingService.listFollowees(new ListFolloweeProfileRequest(id), page, limit);
    return ResponseEntity.ok(listFolloweeProfileResponse);
  }

  @PostMapping
  public ResponseEntity<FollowProfileResponse> folowUser(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @Valid @RequestBody FollowProfileRequest followProfileReq) {
    var followProfileResp = profileFollowingService.follow(userPrincipal, followProfileReq);
    return ResponseEntity.ok(followProfileResp);
  }

  @DeleteMapping
  public ResponseEntity<UnfollowProfileResponse> unfolow(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @Valid @RequestBody UnfollowProfileRequest unfollowProfileReq) {
    var unfollowProfileResp = profileFollowingService.unfollow(userPrincipal, unfollowProfileReq);
    return ResponseEntity.ok(unfollowProfileResp);
  }
}
