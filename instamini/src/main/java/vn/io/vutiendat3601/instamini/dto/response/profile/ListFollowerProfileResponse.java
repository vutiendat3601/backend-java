package vn.io.vutiendat3601.instamini.dto.response.profile;

import java.util.List;
import vn.io.vutiendat3601.instamini.dto.ProfileDto;

public record ListFollowerProfileResponse(
    List<ProfileDto> followers, Long profileFolloweeId, Long totalPage) {}
