package vn.io.vutiendat3601.instamini.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
@Table(name = "post")
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "image_file_path")
  private String imageFilePath;

  @Column(name = "caption")
  private String caption;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @ManyToOne
  @JoinColumn(name = "created_by_id", nullable = false)
  private Profile createdBy;

  @Builder.Default
  @OneToMany(mappedBy = "post")
  @JsonProperty("comments")
  private List<Comment> comments = new LinkedList<>();

  @Builder.Default
  @ManyToMany
  @Column(name = "liked_by_profiles")
  private Set<Profile> likedByProfiles = new HashSet<>();
}
