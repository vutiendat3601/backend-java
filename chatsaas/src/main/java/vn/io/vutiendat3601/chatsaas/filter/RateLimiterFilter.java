package vn.io.vutiendat3601.chatsaas.filter;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.X_API_KEY_HEADER;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

@Slf4j
public class RateLimiterFilter implements Filter {
  private static final long MAX_REQUESTS_PER_MINUTE = 5;
  private final RedisTemplate<String, String> redisTemplate;

  public RateLimiterFilter(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  private boolean exceededRateLimit(String apiKey, int currentMinute) {
    var key = "rate_limiter:%s:counter:%s:minute:%d".formatted(apiKey, apiKey, currentMinute);
    long counter = redisTemplate.opsForValue().increment(key);
    if (counter > MAX_REQUESTS_PER_MINUTE) {
      log.warn("API key '{}' exceeded rate limit: counter={}", apiKey, counter);
      return true;
    }
    redisTemplate.expire(key, Duration.ofMinutes(1));
    return false;
  }

  @Override
  public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain chain)
      throws IOException, ServletException {
    log.info("Checking rate limit for incoming request");
    var req = (HttpServletRequest) servletReq;
    var resp = (HttpServletResponse) servletResp;
    var apiKey = req.getHeader(X_API_KEY_HEADER);
    var currentMinute = LocalTime.now().getMinute();
    if (exceededRateLimit(apiKey, currentMinute)) {
      resp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      return;
    }
    log.info("Request is within rate limit.");
    chain.doFilter(req, resp);
  }
}
