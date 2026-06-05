package vn.io.vutiendat3601.rankingsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class FilterConfiguration {
  @Bean
  CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    var commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
    commonsRequestLoggingFilter.setIncludeQueryString(true);
    commonsRequestLoggingFilter.setIncludePayload(true);
    commonsRequestLoggingFilter.setMaxPayloadLength(1000);
    commonsRequestLoggingFilter.setIncludeHeaders(false);
    commonsRequestLoggingFilter.setAfterMessagePrefix("Request: ");
    return commonsRequestLoggingFilter;
  }
}
