package vn.io.vutiendat3601.chatsaas.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import vn.io.vutiendat3601.chatsaas.filter.AuthenticationFilter;
import vn.io.vutiendat3601.chatsaas.filter.RateLimiterFilter;
import vn.io.vutiendat3601.chatsaas.service.AppService;

@Configuration
public class FilterConfiguration {
  @Bean
  FilterRegistrationBean<AuthenticationFilter> loggingFilter(AppService appService) {
    FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AuthenticationFilter(appService));
    registrationBean.addUrlPatterns("/v1/*");
    registrationBean.setOrder(2);

    return registrationBean;
  }

  @Bean
  FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter(
      RedisTemplate<String, String> redisTemplate) {
    FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new RateLimiterFilter(redisTemplate));
    registrationBean.addUrlPatterns("/v1/*");
    registrationBean.setOrder(3);

    return registrationBean;
  }

  @Bean
  CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    var filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setIncludeHeaders(true);
    filter.setMaxPayloadLength(10_000);
    filter.setAfterMessagePrefix("Request: ");
    return filter;
  }
}
