package vn.io.vutiendat3601.pagination.configuration;

import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
  @Bean
  Base64.Encoder base64Encoder() {
    return Base64.getEncoder();
  }

  @Bean
  Base64.Decoder base64Decoder() {
    return Base64.getDecoder();
  }
}
