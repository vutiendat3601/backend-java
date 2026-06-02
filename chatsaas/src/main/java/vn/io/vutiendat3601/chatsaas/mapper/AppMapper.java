package vn.io.vutiendat3601.chatsaas.mapper;

import org.springframework.stereotype.Component;

import vn.io.vutiendat3601.chatsaas.dto.app.AppDto;
import vn.io.vutiendat3601.chatsaas.entity.App;

@Component
public class AppMapper {
  public AppDto mapToAppDto(App app) {
    return new AppDto(
        app.getId(), app.getApiKey(), app.getName(), app.getIsActive(), app.getCreatedAt());
  }

  public App mapToApp(AppDto appDto) {
    return App.builder()
        .id(appDto.id())
        .apiKey(appDto.apiKey())
        .name(appDto.name())
        .isActive(appDto.isActive())
        .createdAt(appDto.createdAt())
        .build();
  }
}
