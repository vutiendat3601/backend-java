package vn.io.vutiendat3601.caching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.caching.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
