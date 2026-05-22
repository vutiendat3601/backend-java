package vn.io.vutiendat3601.caching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.caching.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
