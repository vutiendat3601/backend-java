package vn.io.vutiendat3601.instamini.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "password", nullable = true)
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "picture_url")
  private String pictureUrl;

  @Column(unique = true)
  private String username;

  @Builder.Default
  @Column(name = "account_non_expired", nullable = false)
  private Boolean accountNonExpired = true;

  @Builder.Default
  @Column(name = "account_non_locked", nullable = false)
  private Boolean accountNonLocked = true;

  @Builder.Default
  @Column(name = "credentials_non_expired", nullable = false)
  private Boolean credentialsNonExpired = true;

  @Column(name = "provider", nullable = true)
  private String provider;

  @Column(name = "user_provider_id", nullable = true)
  private String providerUserId;

  @Builder.Default
  @Column(name = "enabled", nullable = false)
  private Boolean enabled = true;

  @Builder.Default
  @OneToMany
  @JoinColumn(name = "user_id")
  private Set<Authority> authorities = new HashSet<>();
}
