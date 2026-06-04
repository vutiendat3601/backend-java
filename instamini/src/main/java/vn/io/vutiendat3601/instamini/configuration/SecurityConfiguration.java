package vn.io.vutiendat3601.instamini.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  // private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
  private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
// OAuth2LoginAuthenticationFilter s
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.warn("Configuring http filterChain");
    http.authorizeHttpRequests(
            authorize -> authorize.requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll())
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .oauth2Login(
            oauth2 ->
                oauth2.userInfoEndpoint(
                    infoEndpoint -> infoEndpoint.oidcUserService(oidcUserService)
                    // .userService(oAuth2UserService)
                    ))
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
