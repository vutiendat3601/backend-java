package vn.io.vutiendat3601.chatsaas.controller;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.AUTHENTICATED_APP_REQUEST_ATTRIBUTE;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.channel.CreateChannelRequest;
import vn.io.vutiendat3601.chatsaas.dto.channel.CreateChannelResponse;
import vn.io.vutiendat3601.chatsaas.dto.channel.GetChannelResponse;
import vn.io.vutiendat3601.chatsaas.service.ChannelService;

@RestController
@Timed(histogram = true)
@RequiredArgsConstructor
@RequestMapping("v1/channels")
public class ChannelController extends AbstractController {
  private final ChannelService channelService;

  @GetMapping("{id}")
  public ResponseEntity<GetChannelResponse> getChannelById(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto, @PathVariable UUID id) {
    var channelDto = channelService.getChannelById(appDto, id);
    return ResponseEntity.ok(new GetChannelResponse(channelDto));
  }

  @GetMapping("{clientReferenceId}/by-reference-id")
  public ResponseEntity<GetChannelResponse> getChannelByReferenceId(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @PathVariable String clientReferenceId) {
    var channelDto = channelService.getChannelByReferenceId(appDto, clientReferenceId);
    return ResponseEntity.ok(new GetChannelResponse(channelDto));
  }

  @PostMapping("/")
  public ResponseEntity<CreateChannelResponse> createChannel(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      @Valid @RequestBody CreateChannelRequest createChannelReq) {
    var channelDto = channelService.createChannel(appDto, createChannelReq);
    return ResponseEntity.ok(new CreateChannelResponse(channelDto));
  }
}
