package vn.io.vutiendat3601.chatsaas.service;

import org.springframework.cache.annotation.Cacheable;

import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;

public interface AppService {
  @Cacheable(cacheNames = "app", key = "#apiKey")
  AppDto getAppByApiKey(String apiKey);
}
