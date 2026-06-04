package vn.io.vutiendat3601.instamini.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class FilterConfiguration {
  @Bean
  CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    var filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(1000);
    filter.setIncludeHeaders(false);
    filter.setAfterMessagePrefix("Request: ");
    return filter;
  }
}
