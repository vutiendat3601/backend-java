package vn.io.vutiendat3601.rankingsystem.dto.response;

import java.util.List;
import vn.io.vutiendat3601.rankingsystem.dto.RankingDto;

public record GetTopRankingResponse(List<RankingDto> rankings, Long top) {}
