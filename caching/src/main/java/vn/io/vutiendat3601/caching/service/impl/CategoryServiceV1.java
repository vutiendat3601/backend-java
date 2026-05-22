package vn.io.vutiendat3601.caching.service.impl;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import vn.io.vutiendat3601.caching.dto.CategoryArticlesDto;
import vn.io.vutiendat3601.caching.dto.CategoryArticlesRequest;
import vn.io.vutiendat3601.caching.exception.CategoryNotFoundException;
import vn.io.vutiendat3601.caching.mapper.ArticleMapper;
import vn.io.vutiendat3601.caching.repository.CategoryRepository;
import vn.io.vutiendat3601.caching.service.CategoryService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceV1 implements CategoryService {
  private static final int CACHE_TIME_IN_MINUTE = 5;
  private final RedisTemplate<String, String> redisTemplate;
  private final CategoryRepository categoryRepository;
  private final ArticleMapper articleMapper;
  private final ObjectMapper objectMapper;

  @Transactional
  @Override
  public CategoryArticlesDto getArticles(CategoryArticlesRequest categoryArticlesReq)
      throws CategoryNotFoundException {
    var categoryId = categoryArticlesReq.categoryId();
    var cacheKey = "category:%d:articles".formatted(categoryId);
    var cachedData = redisTemplate.opsForValue().get(cacheKey);
    var categoryArticlesResponse = new CategoryArticlesDto(List.of());
    if (Objects.nonNull(cachedData)) {
      log.info("Cache hit");
      categoryArticlesResponse = objectMapper.readValue(cachedData, CategoryArticlesDto.class);
    } else {
      log.info("Cache missed, get from Disk Database");
      var category =
          categoryRepository
              .findById(categoryId)
              .orElseThrow(
                  () ->
                      new CategoryNotFoundException(
                          "Category not found: categoryId=%d".formatted(categoryId)));
      var articleDtos =
          category.getArticles().stream().map(articleMapper::mapToArticleDto).toList();
      categoryArticlesResponse = new CategoryArticlesDto(articleDtos);
      redisTemplate
          .opsForValue()
          .set(
              cacheKey,
              objectMapper.writeValueAsString(categoryArticlesResponse),
              Duration.ofMinutes(CACHE_TIME_IN_MINUTE));
    }
    return categoryArticlesResponse;
  }
}
