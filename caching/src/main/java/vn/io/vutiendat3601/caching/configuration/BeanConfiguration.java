package vn.io.vutiendat3601.caching.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfiguration {
  @Bean
  ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.registeredModules();
    return objectMapper;
  }
}
