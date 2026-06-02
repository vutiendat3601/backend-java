package vn.io.vutiendat3601.chatsaas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.message.ListMessageRequest;
import vn.io.vutiendat3601.chatsaas.dto.message.MessageDto;
import vn.io.vutiendat3601.chatsaas.dto.message.SendMessageRequest;
import vn.io.vutiendat3601.chatsaas.entity.Message;
import vn.io.vutiendat3601.chatsaas.exception.UserNotFoundException;
import vn.io.vutiendat3601.chatsaas.mapper.MessageMapper;
import vn.io.vutiendat3601.chatsaas.repository.ChannelRepository;
import vn.io.vutiendat3601.chatsaas.repository.MessageRepository;
import vn.io.vutiendat3601.chatsaas.repository.UserRepository;
import vn.io.vutiendat3601.chatsaas.service.MessageService;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageServiceV1 implements MessageService {
  private final MessageMapper messageMapper;
  private final MessageRepository messageRepository;
  private final ChannelRepository channelRepository;
  private final UserRepository userRepository;

  @Override
  public MessageDto sendMessage(AppDto appDto, UUID channelId, SendMessageRequest sendMessageReq) {
    var clientUserId = sendMessageReq.clientUserId();
    var user =
        userRepository
            .findByAppIdAndClientUserId(appDto.id(), clientUserId)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        "User not found: clientUserId=%s".formatted(clientUserId)));
    var channelRef = channelRepository.getReferenceById(channelId);
    var message =
        Message.builder()
            .channel(channelRef)
            .user(user)
            .text(sendMessageReq.content())
            .imgUrl(sendMessageReq.imgUrl())
            .build();
    message = messageRepository.save(message);
    return messageMapper.mapToMessageDto(message);
  }

  @Override
  public List<MessageDto> listMessages(AppDto appDto, ListMessageRequest listMessageReq) {
    var messages = new ArrayList<Message>();
    if (listMessageReq.pivotId() == 0) {
      return messageRepository
          .listLatestMessages(listMessageReq.channelId(), listMessageReq.prevLimit())
          .stream()
          .map(messageMapper::mapToMessageDto)
          .toList();
    }
    if (listMessageReq.prevLimit() > 0) {
      messages.addAll(
          messageRepository.listMessagesBeforeId(
              listMessageReq.pivotId(), listMessageReq.channelId(), listMessageReq.prevLimit()));
    }
    if (listMessageReq.nextLimit() > 0) {
      messages.addAll(
          messageRepository.listMessagesAfterId(
              listMessageReq.pivotId(), listMessageReq.channelId(), listMessageReq.nextLimit()));
    }
    return messages.stream().map(messageMapper::mapToMessageDto).toList();
  }
}
