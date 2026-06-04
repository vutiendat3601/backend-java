package vn.io.vutiendat3601.instamini.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.instamini.dto.CommentDto;
import vn.io.vutiendat3601.instamini.entity.Comment;

@RequiredArgsConstructor
@Component
public class CommentMapper {
  private final ProfileMapper profileMapper;

  public CommentDto mapToCommentDto(Comment comment) {
    var createdBy = profileMapper.mapToProfileDto(comment.getCreatedBy());
    return new CommentDto(comment.getId(), comment.getContent(), comment.getCreatedAt(), createdBy);
  }
}
