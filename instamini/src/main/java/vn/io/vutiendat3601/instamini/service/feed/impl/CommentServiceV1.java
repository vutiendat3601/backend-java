package vn.io.vutiendat3601.instamini.service.feed.impl;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.io.vutiendat3601.instamini.dto.request.feed.CreateCommentRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.DeleteCommentRequest;
import vn.io.vutiendat3601.instamini.dto.response.feed.CreateCommentResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.DeleteCommentResponse;
import vn.io.vutiendat3601.instamini.entity.Comment;
import vn.io.vutiendat3601.instamini.exception.CommentNotFoundException;
import vn.io.vutiendat3601.instamini.exception.NoPermissionException;
import vn.io.vutiendat3601.instamini.exception.PostNotFoundException;
import vn.io.vutiendat3601.instamini.mapper.PostMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.CommentRepository;
import vn.io.vutiendat3601.instamini.repository.PostRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.feed.CommentService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@RequiredArgsConstructor
@Service
public class CommentServiceV1 implements CommentService {
  private final ProfileService profileService;
  private final CommentRepository commentRepository;
  private final ProfileRepository profileRepository;
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Transactional
  @Override
  public CreateCommentResponse createComment(
      UserPrincipal userPrincipal, CreateCommentRequest createCommentReq) {
    var postId = createCommentReq.postId();
    var profileDto = profileService.getProfile(userPrincipal).profile();
    var profile = profileRepository.getReferenceById(profileDto.id());
    var post =
        postRepository
            .findById(postId)
            .orElseThrow(
                () -> new PostNotFoundException("Post not found: postId=%d".formatted(postId)));
    var comment =
        Comment.builder()
            .content(createCommentReq.content())
            .createdAt(Instant.now())
            .createdBy(profile)
            .post(post)
            .build();
    commentRepository.save(comment);
    var postDto = postMapper.mapToPostDto(post);
    return new CreateCommentResponse(postDto);
  }

  @Override
  public DeleteCommentResponse deleteComment(
      UserPrincipal userPrincipal, DeleteCommentRequest deleteCommentReq) {
    var profileDto = profileService.getProfile(userPrincipal).profile();
    var comment =
        commentRepository
            .findById(deleteCommentReq.id())
            .orElseThrow(
                () ->
                    new CommentNotFoundException(
                        "Comment not found: commentId=%d".formatted(deleteCommentReq.id())));
    if (!Objects.equals(comment.getCreatedBy().getId(), profileDto.id())) {
      throw new NoPermissionException("Only the owner of a comment can delete it.");
    }
    commentRepository.delete(comment);
    var postDto = postMapper.mapToPostDto(comment.getPost());
    return new DeleteCommentResponse(postDto);
  }
}
