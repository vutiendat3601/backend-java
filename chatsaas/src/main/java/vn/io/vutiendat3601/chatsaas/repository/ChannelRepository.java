package vn.io.vutiendat3601.chatsaas.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.io.vutiendat3601.chatsaas.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
  Optional<Channel> findByAppIdAndClientReferenceId(UUID appId, String clientReferenceId);
}
