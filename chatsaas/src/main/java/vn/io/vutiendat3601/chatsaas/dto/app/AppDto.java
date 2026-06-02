package vn.io.vutiendat3601.chatsaas.dto.app;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonIncludeProperties({"id", "apiKey", "name", "isActive", "createdAt"})
public record AppDto(UUID id, String apiKey, String name, Boolean isActive, Instant createdAt) {}
