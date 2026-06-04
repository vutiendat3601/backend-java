package vn.io.vutiendat3601.instamini.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.io.vutiendat3601.instamini.entity.ProfileFollowing;

public interface ProfileFollowingRepository extends JpaRepository<ProfileFollowing, Long> {
  long countByFolloweeProfileId(long followeeProfileId);

  @Query(
      value =
"""
SELECT * FROM profile_following WHERE followee_profile_id = :followeeProfileId LIMIT :limit OFFSET :offset
""",
      nativeQuery = true)
  List<ProfileFollowing> findByFolloweeProfileId(
      @Param(value = "followeeProfileId") long followeeProfileId,
      @Param(value = "limit") long limit,
      @Param(value = "offset") long offset);

  long countByFollowerProfileId(long followerProfileId);

  @Query(
      value =
"""
SELECT * FROM profile_following WHERE follower_profile_id = :followerProfileId LIMIT :limit OFFSET :offset
""",
      nativeQuery = true)
  List<ProfileFollowing> findByFollowerProfileId(
      @Param(value = "followerProfileId") long followerProfileId,
      @Param(value = "limit") long limit,
      @Param(value = "offset") long offset);

  @Query(
      value =
"""
SELECT * FROM profile_following WHERE follower_profile_id = :followerProfileId
""",
      nativeQuery = true)
  List<ProfileFollowing> findByFollowerProfileId(
      @Param(value = "followerProfileId") long followerProfileId);

  Optional<ProfileFollowing> findByFollowerProfileIdAndFolloweeProfileId(
      long followerProfileId, long followeeProfileId);

  void deleteByFollowerProfileIdAndFolloweeProfileId(
      long followerProfileId, long followeeProfileId);
}
