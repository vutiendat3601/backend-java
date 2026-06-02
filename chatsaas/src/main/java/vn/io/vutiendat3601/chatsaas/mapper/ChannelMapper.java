package vn.io.vutiendat3601.chatsaas.mapper;

import org.springframework.stereotype.Component;

import vn.io.vutiendat3601.chatsaas.dto.channel.ChannelDto;
import vn.io.vutiendat3601.chatsaas.entity.Channel;

@Component
public class ChannelMapper {
  public ChannelDto mapToChannelDto(Channel channel) {
    return new ChannelDto(
        channel.getId(),
        channel.getName(),
        channel.getApp().getId(),
        channel.getClientReferenceId(),
        channel.getCreatedAt());
  }
}
