package vn.io.vutiendat3601.rankingsystem.service;

import vn.io.vutiendat3601.rankingsystem.dto.request.GetTopRankingRequest;
import vn.io.vutiendat3601.rankingsystem.dto.request.RankingUpdateRequest;
import vn.io.vutiendat3601.rankingsystem.dto.response.GetTopRankingResponse;
import vn.io.vutiendat3601.rankingsystem.dto.response.RankingUpdateResponse;

public interface RankingService {
  RankingUpdateResponse updateRanking(RankingUpdateRequest rankingUpdateReq);

  GetTopRankingResponse getTopRankings(GetTopRankingRequest getTopRankingReq);
}
