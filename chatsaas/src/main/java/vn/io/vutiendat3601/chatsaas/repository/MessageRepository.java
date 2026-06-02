package vn.io.vutiendat3601.chatsaas.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.io.vutiendat3601.chatsaas.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
  @Query(
      value =
"""
SELECT * FROM message WHERE channel_id = :channelId
ORDER BY id DESC LIMIT :limit
""",
      nativeQuery = true)
  List<Message> listLatestMessages(
      @Param("channelId") UUID channelId, @Param(value = "limit") Integer limit);

  @Query(
      value =
"""
SELECT * FROM message WHERE id < :id and channel_id = :channelId
ORDER BY id DESC LIMIT :limit
""",
      nativeQuery = true)
  List<Message> listMessagesBeforeId(
      @Param("id") Long id,
      @Param("channelId") UUID channelId,
      @Param(value = "limit") Integer limit);

  @Query(
      value =
"""
SELECT * FROM message WHERE id > :id and channel_id = :channelId
ORDER BY id ASC LIMIT :limit
""",
      nativeQuery = true)
  List<Message> listMessagesAfterId(
      @Param("id") Long id,
      @Param("channelId") UUID channelId,
      @Param(value = "limit") Integer limit);
}
