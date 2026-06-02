package vn.io.vutiendat3601.chatsaas.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.channel.ChannelDto;
import vn.io.vutiendat3601.chatsaas.dto.channel.CreateChannelRequest;
import vn.io.vutiendat3601.chatsaas.entity.Channel;
import vn.io.vutiendat3601.chatsaas.exception.ChannelExistedException;
import vn.io.vutiendat3601.chatsaas.exception.ChannelNotFoundException;
import vn.io.vutiendat3601.chatsaas.mapper.AppMapper;
import vn.io.vutiendat3601.chatsaas.mapper.ChannelMapper;
import vn.io.vutiendat3601.chatsaas.repository.ChannelRepository;
import vn.io.vutiendat3601.chatsaas.service.ChannelService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChannelServiceV1 implements ChannelService {
  private final ChannelMapper channelMapper;
  private final AppMapper appMapper;
  private final ChannelRepository channelRepository;

  @Override
  public ChannelDto createChannel(AppDto appDto, CreateChannelRequest createChannelReq) {
    log.info("Create channel: app={}, createChannelRequest={}", appDto, createChannelReq);
    var appId = appDto.id();
    var app = appMapper.mapToApp(appDto);
    if (channelRepository
        .findByAppIdAndClientReferenceId(appId, createChannelReq.clientReferenceId())
        .isPresent()) {
      throw new ChannelExistedException("Channel existed: id");
    }
    var channel =
        Channel.builder()
            .clientReferenceId(createChannelReq.clientReferenceId())
            .name(createChannelReq.name())
            .createdAt(Instant.now())
            .app(app)
            .build();
    channel = channelRepository.save(channel);
    log.info("Saved {}", channel.getId());
    return channelMapper.mapToChannelDto(channel);
  }

  @Override
  public ChannelDto getChannelByReferenceId(AppDto appDto, String clientReferenceId) {
    log.info("Find channel app={} clientReferenceId={}", appDto, clientReferenceId);
    var appId = appDto.id();
    var channel =
        channelRepository
            .findByAppIdAndClientReferenceId(appId, clientReferenceId)
            .orElseThrow(
                () ->
                    new ChannelNotFoundException(
                        "Channel not found: appId=%s,clientReferenceId=%s"
                            .formatted(appId.toString(), clientReferenceId.toString())));
    return channelMapper.mapToChannelDto(channel);
  }

  @Override
  public ChannelDto getChannelById(AppDto appDto, UUID id) {
    var appId = appDto.id();
    var channel =
        channelRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ChannelNotFoundException(
                        "Channel not found: appId=%s,id=%s".formatted(appId, id)));
    return channelMapper.mapToChannelDto(channel);
  }
}
