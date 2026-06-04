package vn.io.vutiendat3601.instamini.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.io.vutiendat3601.instamini.entity.User;
import vn.io.vutiendat3601.instamini.model.OidcUserPrincipal;
import vn.io.vutiendat3601.instamini.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnhancedOidcUserService extends OidcUserService {
  private final UserRepository userRepository;

  @Override
  public OidcUser loadUser(OidcUserRequest oidcUserReq) throws OAuth2AuthenticationException {
    log.info("Load OIDC user: oidcUserReq={}", oidcUserReq);
    var oidcUser = super.loadUser(oidcUserReq);
    return processOAuth2User(oidcUserReq, oidcUser);
  }

  @Transactional
  private OidcUser processOAuth2User(OidcUserRequest oidcUserReq, OidcUser oidcUser) {
    var oidcUserInfo = oidcUser.getUserInfo();
    var userOpt = userRepository.findByUsername(oidcUserInfo.getEmail());
    var user =
        userOpt
            .map(u -> updateUser(u, oidcUserInfo))
            .orElseGet(() -> createUser(oidcUserReq, oidcUserInfo));
    return OidcUserPrincipal.create(user, oidcUser, oidcUser.getAttributes());
  }

  private User updateUser(User user, OidcUserInfo oidcUserInfo) {
    user.setName(oidcUserInfo.getFullName());
    user.setPictureUrl(oidcUserInfo.getPicture());
    return userRepository.save(user);
  }

  private User createUser(OidcUserRequest oidcUserReq, OidcUserInfo oidcUserInfo) {
    var user =
        User.builder()
            .id(UUID.randomUUID())
            .provider(oidcUserReq.getClientRegistration().getRegistrationId())
            .providerUserId(oidcUserInfo.getSubject())
            .name(oidcUserInfo.getFullName())
            .username(oidcUserInfo.getEmail())
            .pictureUrl(oidcUserInfo.getPicture())
            .build();
    return userRepository.save(user);
  }
}
