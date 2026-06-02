package vn.io.vutiendat3601.chatsaas.service;

import java.util.UUID;

import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.channel.ChannelDto;
import vn.io.vutiendat3601.chatsaas.dto.channel.CreateChannelRequest;

public interface ChannelService {
  ChannelDto createChannel(AppDto appDto, CreateChannelRequest createChannelRequest);

  ChannelDto getChannelByReferenceId(AppDto appDto, String clientReferenceId);

  ChannelDto getChannelById(AppDto appDto, UUID id);
}
