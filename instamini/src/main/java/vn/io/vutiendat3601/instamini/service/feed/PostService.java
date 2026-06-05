package vn.io.vutiendat3601.instamini.service.feed;

import vn.io.vutiendat3601.instamini.dto.request.feed.CreatePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.DeletePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.GetPostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.LikePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.ListPostRequest;
import vn.io.vutiendat3601.instamini.dto.request.feed.UnlikePostRequest;
import vn.io.vutiendat3601.instamini.dto.response.feed.CreatePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.DeletePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.GetPostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.LikePostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.ListUserPostResponse;
import vn.io.vutiendat3601.instamini.dto.response.feed.UnlikePostResponse;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;

public interface PostService {
  CreatePostResponse createPost(UserPrincipal userPrincipal, CreatePostRequest createPostReq);

  GetPostResponse getPost(GetPostRequest getPostReq);

  DeletePostResponse deletePost(UserPrincipal userPrincipal, DeletePostRequest deletePostReq);

  LikePostResponse likePost(UserPrincipal userPrincipal, LikePostRequest likePostReq);

  UnlikePostResponse unlikePost(UserPrincipal userPrincipal, UnlikePostRequest unlikePostReq);

  ListUserPostResponse listPosts(ListPostRequest listUserPostReq);
}
