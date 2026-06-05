package vn.io.vutiendat3601.rankingsystem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.rankingsystem.dto.request.GetTopRankingRequest;
import vn.io.vutiendat3601.rankingsystem.dto.request.RankingUpdateRequest;
import vn.io.vutiendat3601.rankingsystem.dto.response.GetTopRankingResponse;
import vn.io.vutiendat3601.rankingsystem.dto.response.RankingUpdateResponse;
import vn.io.vutiendat3601.rankingsystem.service.RankingService;

@RequiredArgsConstructor
@RequestMapping("v1/rankings")
@RestController
public class RankingController {
  private final RankingService rankingService;

  @PostMapping
  public ResponseEntity<RankingUpdateResponse> updateRanking(
      @RequestBody RankingUpdateRequest rankingUpdateReq) {
    var rankingUpdateResp = rankingService.updateRanking(rankingUpdateReq);
    return ResponseEntity.ok(rankingUpdateResp);
  }

  @GetMapping("top/{top}")
  public ResponseEntity<GetTopRankingResponse> getTopRankings(
      @PathVariable(name = "top") Long top) {
    var getTopRankingResp = rankingService.getTopRankings(new GetTopRankingRequest(top));
    return ResponseEntity.ok(getTopRankingResp);
  }
}
