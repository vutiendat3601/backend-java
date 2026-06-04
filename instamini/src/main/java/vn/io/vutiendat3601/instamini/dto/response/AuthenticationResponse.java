package vn.io.vutiendat3601.instamini.dto.response;

import org.springframework.security.oauth2.core.user.OAuth2User;

public record AuthenticationResponse<T extends OAuth2User>(T principal) {}
