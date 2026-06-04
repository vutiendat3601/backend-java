package vn.io.vutiendat3601.instamini.controller.feed;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.instamini.dto.request.feed.CreatePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.DeletePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.GetPostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.LikePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.ListUserPostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.UnlikePostRequest;
import vn.io.vutiendat3601.instamini.dto.response.feed.CreatePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.DeletePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.GetPostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.LikePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListUserPostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.UnlikePostResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.service.feed.PostService;

@Tag(name = "Post")
@Slf4j
@RequestMapping("v1/posts")
@RequiredArgsConstructor
@RestController
public class PostController {
  private final PostService postService;

  @PostMapping
  public ResponseEntity<CreatePostResponse> createPost(
      @Valid @RequestBody CreatePostRequest createPostReq,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    var createPostResp = postService.createPost(userPrincipal, createPostReq);
    return ResponseEntity.ok(createPostResp);
  }

  @GetMapping("{id}")
  public ResponseEntity<GetPostResponse> getPost(@PathVariable("id") Long id) {
    var getPostResp = postService.getPost(new GetPostRequest(id));
    return ResponseEntity.ok(getPostResp);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<DeletePostResponse> deletePost(
      @PathVariable Long id, Authentication authentication) {
    var userPrincipal = (UserPrincipal) authentication.getPrincipal();
    var deletePostResp = postService.deletePost(userPrincipal, new DeletePostRequest(id));
    return ResponseEntity.ok(deletePostResp);
  }

  @PostMapping("{id}/like")
  public ResponseEntity<LikePostResponse> likePost(
      @PathVariable Long id, Authentication authentication) {
    var userPrincipal = (UserPrincipal) authentication.getPrincipal();
    var likePostResp = postService.likePost(userPrincipal, new LikePostRequest(id));
    return ResponseEntity.ok(likePostResp);
  }

  @DeleteMapping("{id}/like")
  public ResponseEntity<UnlikePostResponse> unlikePost(
      @PathVariable Long id, Authentication authentication) {
    var userPrincipal = (UserPrincipal) authentication.getPrincipal();
    var unlikePostResp = postService.unlikePost(userPrincipal, new UnlikePostRequest(id));
    return ResponseEntity.ok(unlikePostResp);
  }

  @GetMapping("created-by/{profileId}")
  public ResponseEntity<ListUserPostResponse> getUserPosts(
      @PathVariable(name = "profileId") Long profileId) {
    var listUserPostResp = postService.getUserPosts(new ListUserPostRequest(profileId));
    return ResponseEntity.ok(listUserPostResp);
  }
}
