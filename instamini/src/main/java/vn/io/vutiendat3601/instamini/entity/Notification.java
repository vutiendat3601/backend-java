package vn.io.vutiendat3601.instamini.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.instamini.constant.NotificationType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@Entity
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_profile", nullable = false)
  private Profile fromProfile;

  @ManyToOne
  @JoinColumn(name = "to_profile", nullable = false)
  private Profile toProfile;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  private Instant createdAt;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;
}
