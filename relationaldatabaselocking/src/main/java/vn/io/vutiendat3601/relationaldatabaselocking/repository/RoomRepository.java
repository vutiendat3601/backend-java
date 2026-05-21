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
  Optional<Room> findByIdAndAvailable(long id, boolean available);

  // Optimistic locking
  Optional<Room> findOneByIdAndAvailable(long id, boolean available);

  @Modifying
  @Query("UPDATE room SET available = false WHERE id = :id")
  int updateRoomAsUnavailableUsingPessimisticLocking(@Param("id") long id);

  @Modifying
  @Query(
"""
UPDATE room SET available = false, version = version + 1
WHERE id = :id AND version = :version
""")
  long updateRoomAsUnavailableUsingOptimisticLocking(
      @Param("id") long id, @Param("version") long version);
}
