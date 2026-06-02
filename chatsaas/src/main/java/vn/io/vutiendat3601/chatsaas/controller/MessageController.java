package vn.io.vutiendat3601.chatsaas.controller;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.AUTHENTICATED_APP_REQUEST_ATTRIBUTE;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.message.ListMessageRequest;
import vn.io.vutiendat3601.chatsaas.dto.message.ListMessageResponse;
import vn.io.vutiendat3601.chatsaas.dto.message.SendMessageRequest;
import vn.io.vutiendat3601.chatsaas.dto.message.SendMessageResponse;
import vn.io.vutiendat3601.chatsaas.service.MessageService;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("v1/messages")
public class MessageController extends AbstractController {
  private final MessageService messageService;

  @PostMapping("{channelId}")
  public ResponseEntity<SendMessageResponse> sendMessage(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @PathVariable UUID channelId,
      @Valid @RequestBody SendMessageRequest sendMessageReq) {
    var messageDto = messageService.sendMessage(appDto, channelId, sendMessageReq);
    return ResponseEntity.ok(new SendMessageResponse(messageDto));
  }

  @GetMapping("{channelId}")
  public ResponseEntity<ListMessageResponse> getMessages(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @PathVariable UUID channelId,
      @RequestParam("pivotId") Long pivotId,
      @RequestParam("prevLimit") Integer prevLimit,
      @RequestParam("nextLimit") Integer nextLimit) {
    var messageDtos =
        messageService.listMessages(
            appDto, new ListMessageRequest(channelId, pivotId, prevLimit, nextLimit));
    return ResponseEntity.ok(new ListMessageResponse(messageDtos));
  }
}
