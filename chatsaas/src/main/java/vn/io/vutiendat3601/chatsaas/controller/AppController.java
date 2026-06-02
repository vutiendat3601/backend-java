package vn.io.vutiendat3601.chatsaas.controller;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.AUTHENTICATED_APP_REQUEST_ATTRIBUTE;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.dto.app.InspectAppResponse;

@RestController
@Timed(histogram = true)
@RequiredArgsConstructor
@RequestMapping("v1/auth")
@Slf4j
public class AppController extends AbstractController {
  @GetMapping("inspect")
  public ResponseEntity<InspectAppResponse> inspectApp(
      @RequestAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE) AppDto appDto,
      HttpServletRequest request) {
    log.info("class = {}, app = {}", appDto.getClass(), appDto);
    return ResponseEntity.ok(new InspectAppResponse(appDto));
  }
}
