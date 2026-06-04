package vn.io.vutiendat3601.instamini.service.feed.impl;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.io.vutiendat3601.instamini.dto.request.UploadImageRequest;
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
import vn.io.vutiendat3601.instamini.entity.Post;
import vn.io.vutiendat3601.instamini.exception.NoPermissionException;
import vn.io.vutiendat3601.instamini.exception.PostNotFoundException;
import vn.io.vutiendat3601.instamini.mapper.PostMapper;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.PostRepository;
import vn.io.vutiendat3601.instamini.repository.ProfileRepository;
import vn.io.vutiendat3601.instamini.service.UploadService;
import vn.io.vutiendat3601.instamini.service.feed.PostService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@RequiredArgsConstructor
@Service
public class PostServiceV1 implements PostService {
  private final PostMapper postMapper;

  private final ProfileRepository profileRepository;

  private final ProfileService profileService;

  private final UploadService uploadService;

  private final PostRepository postRepository;

  @Transactional
  @Override
  public CreatePostResponse createPost(
      UserPrincipal userPrincipal, CreatePostRequest createPostReq) {
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    var userPrincipalProfile = profileRepository.getReferenceById(userPrincipalProfileDto.id());
    var imageFilePath =
        uploadService
            .uploadImage(new UploadImageRequest(createPostReq.base64ImageString()))
            .filePath();
    var post =
        Post.builder()
            .caption(createPostReq.caption())
            .createdAt(Instant.now())
            .createdBy(userPrincipalProfile)
            .imageFilePath(imageFilePath)
            .build();
    post = postRepository.save(post);
    var postDto = postMapper.mapToPostDto(post);
    return new CreatePostResponse(postDto);
  }

  @Override
  public GetPostResponse getPost(GetPostRequest getPostReq) {
    var post = getPostById(getPostReq.id());
    return new GetPostResponse(postMapper.mapToPostDto(post));
  }

  @Transactional
  @Override
  public DeletePostResponse deletePost(
      UserPrincipal userPrincipal, DeletePostRequest deletePostReq) {
    var post = getPostById(deletePostReq.id());
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    if (!Objects.equals(post.getCreatedBy().getId(), userPrincipalProfileDto.id())) {
      throw new NoPermissionException("Only the owner of a post can delete it.");
    }
    postRepository.deleteById(deletePostReq.id());
    return new DeletePostResponse();
  }

  @Transactional
  @Override
  public LikePostResponse likePost(UserPrincipal userPrincipal, LikePostRequest likePostReq) {
    var post = getPostById(likePostReq.id());
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    var userPrincipalProfile = profileRepository.getReferenceById(userPrincipalProfileDto.id());
    post.getLikedByProfiles().add(userPrincipalProfile);
    post = postRepository.save(post);
    return new LikePostResponse(postMapper.mapToPostDto(post));
  }

  @Transactional
  @Override
  public UnlikePostResponse unlikePost(
      UserPrincipal userPrincipal, UnlikePostRequest unlikePostReq) {
    var post = getPostById(unlikePostReq.id());
    var userPrincipalProfileDto = profileService.getProfile(userPrincipal).profile();
    var userPrincipalProfile = profileRepository.getReferenceById(userPrincipalProfileDto.id());
    post.getLikedByProfiles().remove(userPrincipalProfile);
    post = postRepository.save(post);
    return new UnlikePostResponse(postMapper.mapToPostDto(post));
  }

  @Override
  public ListUserPostResponse getUserPosts(ListUserPostRequest listUserPostReq) {
    var userPrincipalProfileDto = profileService.getProfile(listUserPostReq.profileId()).profile();
    var userPrincipalProfile = profileRepository.getReferenceById(userPrincipalProfileDto.id());
    var posts = postRepository.findByCreatedBy(userPrincipalProfile);
    var postDtos = posts.stream().map(postMapper::mapToPostDto).toList();
    return new ListUserPostResponse(postDtos, userPrincipalProfileDto);
  }

  // #: Helpers

  private Post getPostById(Long id) {
    return postRepository
        .findById(id)
        .orElseThrow(() -> new PostNotFoundException("Post not found: postId=%d".formatted(id)));
  }

  // # Helpers
}
