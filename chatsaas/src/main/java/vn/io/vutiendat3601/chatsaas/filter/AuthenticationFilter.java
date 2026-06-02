package vn.io.vutiendat3601.chatsaas.filter;

import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.AUTHENTICATED_APP_REQUEST_ATTRIBUTE;
import static vn.io.vutiendat3601.chatsaas.constant.GlobalConstant.X_API_KEY_HEADER;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import vn.io.vutiendat3601.chatsaas.service.AppService;

@Slf4j
public class AuthenticationFilter implements Filter {
  private AppService appService;

  public AuthenticationFilter(AppService appService) {
    this.appService = appService;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    var request = (HttpServletRequest) servletRequest;
    var response = (HttpServletResponse) servletResponse;

    var apiKey = request.getHeader(X_API_KEY_HEADER);
    log.debug("Got API key from header: {}".formatted(apiKey));
    if (Objects.isNull(apiKey)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return;
    }

    var appDto = appService.getAppByApiKey(apiKey);
    if (Objects.isNull(appDto)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return;
    }
    request.setAttribute(AUTHENTICATED_APP_REQUEST_ATTRIBUTE, appDto);

    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}
