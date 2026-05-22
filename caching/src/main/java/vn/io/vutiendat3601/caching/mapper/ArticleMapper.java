package vn.io.vutiendat3601.caching.mapper;

import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.caching.dto.ArticleDto;
import vn.io.vutiendat3601.caching.entity.Article;

@Component
public class ArticleMapper {
  public ArticleDto mapToArticleDto(Article article) {
    return new ArticleDto(
        article.getId(), article.getUrl(), article.getTitle(), article.getContent());
  }
}
