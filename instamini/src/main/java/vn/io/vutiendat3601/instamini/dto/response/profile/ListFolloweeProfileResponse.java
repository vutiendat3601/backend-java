package vn.io.vutiendat3601.instamini.dto.response.profile;

import java.util.List;
import vn.io.vutiendat3601.instamini.dto.ProfileDto;

public record ListFolloweeProfileResponse(
    List<ProfileDto> followees, Long profileFollowerId, Long totalPage) {}
