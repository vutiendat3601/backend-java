package vn.io.vutiendat3601.instamini.service.feed;

import vn.io.vutiendat3601.instamini.dto.response.feed.ListFeedResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface FeedService {
  ListFeedResponse listFeed(UserPrincipal userPrincipal, long page, long limit);
}
