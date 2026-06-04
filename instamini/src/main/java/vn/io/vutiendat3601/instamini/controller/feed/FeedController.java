package vn.io.vutiendat3601.instamini.controller.feed;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListFeedResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.service.feed.FeedService;

@Tag(name = "Feed")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/feeds")
@RestController
public class FeedController {
  private final FeedService feedService;

  @GetMapping
  public ResponseEntity<ListFeedResponse> getFeed(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(name = "page", defaultValue = "1") @Min(1) Long page,
      @RequestParam(name = "limit", defaultValue = "10") @Min(1) Long limit) {
    log.info("page={}, limit={}", page, limit);
    var listFeedResp = feedService.listFeed(userPrincipal, page, limit);
    return ResponseEntity.ok(listFeedResp);
  }
}
