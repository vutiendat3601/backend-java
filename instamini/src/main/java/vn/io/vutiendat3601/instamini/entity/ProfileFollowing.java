package vn.io.vutiendat3601.instamini.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
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
@Table(
    name = "profile_following",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"follower_profile_id", "followee_profile_id"})
    })
@Entity
public class ProfileFollowing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "follower_profile_id", nullable = false)
  private Long followerProfileId;

  @Column(name = "followee_profile_id", nullable = false)
  private Long followeeProfileId;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
