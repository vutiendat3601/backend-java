package vn.io.vutiendat3601.chatsaas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.exception.AppNotFoundException;
import vn.io.vutiendat3601.chatsaas.mapper.AppMapper;
import vn.io.vutiendat3601.chatsaas.repository.AppRepository;
import vn.io.vutiendat3601.chatsaas.service.AppService;

@RequiredArgsConstructor
@Service
public class AppServiceV1 implements AppService {
  private final AppMapper appMapper;
  private final AppRepository appRepository;

  @Override
  @Cacheable(cacheNames = "app", key = "#apiKey")
  public AppDto getAppByApiKey(String apiKey) {
    var app =
        appRepository
            .findByApiKey(apiKey)
            .orElseThrow(
                () -> new AppNotFoundException("App not found: apiKey=%s".formatted(apiKey)));
    return appMapper.mapToAppDto(app);
  }
}
