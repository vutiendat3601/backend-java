package vn.io.vutiendat3601.instamini.service.feed;

import vn.io.vutiendat3601.instamini.dto.request.feed.CreateCommentRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.DeleteCommentRequest;
import vn.io.vutiendat3601.instamini.dto.response.feed.CreateCommentResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.DeleteCommentResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface CommentService {
  CreateCommentResponse createComment(
      UserPrincipal userPrincipal, CreateCommentRequest createCommentReq);

  DeleteCommentResponse deleteComment(
      UserPrincipal userPrincipal, DeleteCommentRequest deleteCommentReq);
}
