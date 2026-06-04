package vn.io.vutiendat3601.instamini.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import vn.io.vutiendat3601.instamini.entity.User;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrincipal implements OAuth2User, UserDetails {
  private UUID id;

  private String username;

  private String password;

  private String name;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  private String providerUserId;

  private boolean enabled;

  private String pictureUrl;

  private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

  private Map<String, Object> attributes = new HashMap<>();

  protected UserPrincipal(
      UUID id,
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal authenticated(User user) {
    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    var userPrincipal =
        new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities);
    userPrincipal.setName(user.getName());
    return userPrincipal;
  }

  public static UserPrincipal authenticated(User user, Map<String, Object> attributes) {
    var userPrincipal = UserPrincipal.authenticated(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }
}
