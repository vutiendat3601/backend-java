package vn.io.vutiendat3601.chatsaas.mapper;

import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.chatsaas.dto.message.MessageDto;
import vn.io.vutiendat3601.chatsaas.entity.Message;

@Component
public class MessageMapper {
  public MessageDto mapToMessageDto(Message message) {
    return new MessageDto(
        message.getId(),
        message.getUser().getClientUserId(),
        message.getUser().getName(),
        message.getChannel().getId(),
        message.getText(),
        message.getImgUrl(),
        message.getIsDeleted(),
        message.getCreatedAt(),
        message.getUpdatedAt());
  }
}
