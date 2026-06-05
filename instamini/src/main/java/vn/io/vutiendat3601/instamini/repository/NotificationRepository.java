package vn.io.vutiendat3601.instamini.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.instamini.entity.Notification;
import vn.io.vutiendat3601.instamini.entity.Profile;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByToProfile(Profile toProfile);
}
