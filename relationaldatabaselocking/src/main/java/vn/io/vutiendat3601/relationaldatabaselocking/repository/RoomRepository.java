package vn.io.vutiendat3601.relationaldatabaselocking.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
  // Pessimistic locking
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Room> findByIdAndAvailable(Long id, Boolean available);

  // Optimistic locking
  Optional<Room> findOneByIdAndAvailable(Long id, Boolean available);

  @Modifying
  @Query("UPDATE room SET available = false WHERE id = :id")
  int updateRoomAsUnavailableUsingPessimisticLocking(@Param("id") Long id);

  @Modifying
  @Query(
"""
UPDATE room SET available = false, version = version + 1
WHERE id = :id AND version = :version
""")
  int updateRoomAsUnavailableUsingOptimisticLocking(
      @Param("id") Long id, @Param("version") Long version);
}
