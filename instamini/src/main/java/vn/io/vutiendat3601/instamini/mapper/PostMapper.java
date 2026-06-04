package vn.io.vutiendat3601.instamini.mapper;

import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_BASE_URL;
import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.instamini.dto.PostDto;
import vn.io.vutiendat3601.instamini.entity.Post;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostMapper {
  private final ProfileMapper profileMapper;
  private final CommentMapper commentMapper;
  private final Environment env;

  public PostDto mapToPostDto(Post post) {
    var minioBaseUrl = env.getProperty("minio.baseUrl", MINIO_DEFAULT_BASE_URL);
    var minioPublicImageBucket =
        env.getProperty("minio.public-image-bucket", MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET);
    var imageUrl = minioBaseUrl + "/" + minioPublicImageBucket + "/" + post.getImageFilePath();
    var createdBy = profileMapper.mapToProfileDto(post.getCreatedBy());
    var comments = post.getComments().stream().map(commentMapper::mapToCommentDto).toList();
    var likedByUsers =
        post.getLikedByProfiles().stream().map(profileMapper::mapToProfileDto).toList();
    return new PostDto(
        post.getId(),
        imageUrl,
        post.getCaption(),
        post.getCreatedAt(),
        createdBy,
        comments,
        likedByUsers);
  }
}
