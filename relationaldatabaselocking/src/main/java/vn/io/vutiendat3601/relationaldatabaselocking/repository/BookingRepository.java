package vn.io.vutiendat3601.relationaldatabaselocking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.relationaldatabaselocking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
