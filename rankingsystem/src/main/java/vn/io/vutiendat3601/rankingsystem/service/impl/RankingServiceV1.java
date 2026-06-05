package vn.io.vutiendat3601.rankingsystem.service.impl;

import static vn.io.vutiendat3601.rankingsystem.constant.CacheConstant.RANKING_KEY;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.rankingsystem.dto.RankingDto;
import vn.io.vutiendat3601.rankingsystem.dto.request.GetTopRankingRequest;
import vn.io.vutiendat3601.rankingsystem.dto.request.RankingUpdateRequest;
import vn.io.vutiendat3601.rankingsystem.dto.response.GetTopRankingResponse;
import vn.io.vutiendat3601.rankingsystem.dto.response.RankingUpdateResponse;
import vn.io.vutiendat3601.rankingsystem.entity.Ranking;
import vn.io.vutiendat3601.rankingsystem.service.RankingService;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankingServiceV1 implements RankingService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public RankingUpdateResponse updateRanking(RankingUpdateRequest rankingUpdateReq) {
    var zSetOps = redisTemplate.opsForZSet();
    zSetOps.add(RANKING_KEY, rankingUpdateReq.user(), rankingUpdateReq.score());
    var data = zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9);
    log.info("data={}", data);
    var topRankings =
        zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9).stream()
            .map(tuple -> new Ranking(tuple.getValue().toString(), tuple.getScore()))
            .collect(Collectors.toList());
    messagingTemplate.convertAndSend("/topic/rankings", topRankings);
    return new RankingUpdateResponse();
  }

  @Override
  public GetTopRankingResponse getTopRankings(GetTopRankingRequest getTopRankingReq) {
    var rankingDtos =
        redisTemplate
            .opsForZSet()
            .reverseRangeWithScores(RANKING_KEY, 0, getTopRankingReq.top())
            .stream()
            .map(tuple -> new RankingDto(tuple.getValue().toString(), tuple.getScore()))
            .collect(Collectors.toList());
    return new GetTopRankingResponse(rankingDtos, getTopRankingReq.top());
  }
}
