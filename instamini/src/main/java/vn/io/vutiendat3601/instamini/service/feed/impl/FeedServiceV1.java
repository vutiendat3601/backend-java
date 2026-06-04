package vn.io.vutiendat3601.instamini.service.feed.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListFeedResponse;
import vn.io.vutiendat3601.instamini.entity.ProfileFollowing;
import vn.io.vutiendat3601.instamini.mapper.PostMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.PostRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileFollowingRepository;
import vn.io.vutiendat3601.instamini.service.feed.FeedService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedServiceV1 implements FeedService {
  private final PostMapper postMapper;

  private final ProfileService profileService;

  private final PostRepository postRepository;

  private final ProfileFollowingRepository profileFollowingRepository;

  @Override
  public ListFeedResponse listFeed(UserPrincipal userPrincipal, long page, long limit) {
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();

    var profileFollowings =
        profileFollowingRepository.findByFollowerProfileId(userPrincipalProfileDto.id());
    var followeeProfileIds =
        profileFollowings.stream().map(ProfileFollowing::getFolloweeProfileId).toList();
    log.info("followeeProfileIds={}", followeeProfileIds);
    long totalPosts = postRepository.countByCreatedByIdIn(followeeProfileIds);
    log.info("totalPosts={}", totalPosts);
    var totalPages = (long) Math.ceil((double) totalPosts / limit);
    var offset = (page - 1L) * limit;

    var postDtos =
        postRepository.findByCreatedByIdIn(followeeProfileIds, limit, offset).stream()
            .map(postMapper::mapToPostDto)
            .toList();

    return new ListFeedResponse(postDtos, totalPages);
  }
}
