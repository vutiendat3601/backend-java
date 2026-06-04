package vn.io.vutiendat3601.instamini.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import vn.io.vutiendat3601.instamini.entity.User;

@EqualsAndHashCode(callSuper = true)
@Data
public class OidcUserPrincipal extends UserPrincipal implements OidcUser {
  private OidcUser delegate;

  private OidcUserPrincipal(
      UUID id,
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      OidcUser delegate) {
    super(id, username, password, authorities);
    this.delegate = delegate;
  }

  @Override
  public Map<String, Object> getClaims() {
    return delegate.getClaims();
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return delegate.getUserInfo();
  }

  @Override
  public OidcIdToken getIdToken() {
    return delegate.getIdToken();
  }

  public static OidcUserPrincipal authenticated(User user, OidcUser oidcUser) {
    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    var oidcUserPrincipal =
        new OidcUserPrincipal(
            user.getId(), user.getUsername(), user.getPassword(), authorities, oidcUser);
    oidcUserPrincipal.setAccountNonExpired(user.getAccountNonExpired());
    oidcUserPrincipal.setAccountNonLocked(user.getAccountNonLocked());
    oidcUserPrincipal.setCredentialsNonExpired(user.getCredentialsNonExpired());
    oidcUserPrincipal.setEnabled(user.getEnabled());
    oidcUserPrincipal.setProviderUserId(user.getProviderUserId());
    oidcUserPrincipal.setName(user.getName());
    oidcUserPrincipal.setPictureUrl(user.getPictureUrl());
    return oidcUserPrincipal;
  }

  public static OidcUserPrincipal create(
      User user, OidcUser oidcUser, Map<String, Object> attributes) {
    var oidcUserPrincipal = authenticated(user, oidcUser);
    oidcUserPrincipal.setAttributes(attributes);
    return oidcUserPrincipal;
  }
}
