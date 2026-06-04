package vn.io.vutiendat3601.instamini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.instamini.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
