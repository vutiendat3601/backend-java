package vn.io.vutiendat3601.instamini.constant;

public interface MessageQueueConstant {
  String TOPIC_EXCHANGE_NAME = "instamini-exchange";
  String QUEUE_AFTER_CREATE_POST = "after-create-post-queue";
  String QUEUE_LIKE_POST = "like-post-queue";
  String QUEUE_LIKE_POST_DLQ = "like-post-queue-dlq";
  String QUEUE_COMMENT_POST = "comment-post-queue";
}
