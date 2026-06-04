package vn.io.vutiendat3601.instamini.dto.response.feed;

import java.util.List;
import vn.io.vutiendat3601.instamini.dto.PostDto;

public record ListFeedResponse(List<PostDto> posts, Long totalPages) {}
