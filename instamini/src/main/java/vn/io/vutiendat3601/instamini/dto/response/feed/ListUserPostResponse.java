package vn.io.vutiendat3601.instamini.dto.response.feed;

import java.util.List;
import vn.io.vutiendat3601.instamini.dto.PostDto;
import vn.io.vutiendat3601.instamini.dto.ProfileDto;

public record ListUserPostResponse(List<PostDto> posts, ProfileDto user) {}
