package vn.io.vutiendat3601.instamini.controller.feed;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.request.feed.CreateCommentRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.DeleteCommentRequest;
import vn.io.vutiendat3601.instamini.dto.response.feed.CreateCommentResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.DeleteCommentResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.service.feed.CommentService;

@Tag(name = "Comment")
@Slf4j
@RequestMapping("v1/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {
  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @Valid @RequestBody CreateCommentRequest createCommentReq) {
    var createCommentResp = commentService.createComment(userPrincipal, createCommentReq);
    return ResponseEntity.ok(createCommentResp);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<DeleteCommentResponse> deleteComment(
      @PathVariable Long id, Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    var deleteCommentResp =
        commentService.deleteComment(userPrincipal, new DeleteCommentRequest(id));
    return ResponseEntity.ok(deleteCommentResp);
  }
}
