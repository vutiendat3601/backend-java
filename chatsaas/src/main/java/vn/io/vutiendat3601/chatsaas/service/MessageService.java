package vn.io.vutiendat3601.chatsaas.service;

import java.util.List;
import java.util.UUID;

import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.message.ListMessageRequest;
import vn.io.vutiendat3601.chatsaas.dto.message.MessageDto;
import vn.io.vutiendat3601.chatsaas.dto.message.SendMessageRequest;

public interface MessageService {
  MessageDto sendMessage(AppDto appDto, UUID channelId, SendMessageRequest sendMessageReq);

  List<MessageDto> listMessages(AppDto appDto, ListMessageRequest listMessageReq);
}
