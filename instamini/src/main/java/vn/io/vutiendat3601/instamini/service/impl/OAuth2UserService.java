package vn.io.vutiendat3601.instamini.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.entity.User;
import vn.io.vutiendat3601.instamini.model.OAuth2UserInfo;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserReq) throws OAuth2AuthenticationException {
    log.info("Load OAuth2 user: oAuth2UserReq={}", oAuth2UserReq);
    var oAuth2User = super.loadUser(oAuth2UserReq);
    return processOAuth2User(oAuth2UserReq, oAuth2User);
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserReq, OAuth2User oAuth2User) {
    var oAuth2UserInfo =
        new OAuth2UserInfo(
            oAuth2User.getAttributes().get("sub") + "",
            oAuth2User.getAttributes().get("name") + "",
            oAuth2User.getAttributes().get("picture") + "",
            oAuth2User.getAttributes().get("email") + "");

    var userOpt = userRepository.findByUsername(oAuth2UserInfo.email());
    var user =
        userOpt
            .map(u -> updateUser(u, oAuth2UserInfo))
            .orElseGet(() -> createUser(oAuth2UserReq, oAuth2UserInfo));
    return UserPrincipal.authenticated(user, oAuth2User.getAttributes());
  }

  private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
    user.setName(oAuth2UserInfo.name());
    user.setPictureUrl(oAuth2UserInfo.picture());
    return userRepository.save(user);
  }

  private User createUser(OAuth2UserRequest oAuth2UserReq, OAuth2UserInfo oAuth2UserInfo) {
    var user =
        User.builder()
            .id(UUID.randomUUID())
            .provider(oAuth2UserReq.getClientRegistration().getRegistrationId())
            .providerUserId(oAuth2UserInfo.id())
            .name(oAuth2UserInfo.name())
            .username(oAuth2UserInfo.email())
            .pictureUrl(oAuth2UserInfo.picture())
            .build();
    return userRepository.save(user);
  }
}
