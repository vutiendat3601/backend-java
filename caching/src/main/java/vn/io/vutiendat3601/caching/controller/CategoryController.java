package vn.io.vutiendat3601.caching.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.caching.dto.CategoryArticlesDto;
import vn.io.vutiendat3601.caching.dto.CategoryArticlesRequest;
import vn.io.vutiendat3601.caching.service.CategoryService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("{id}/articles")
  public ResponseEntity<CategoryArticlesDto> getArticles(@PathVariable Long id) {
    log.info("request id={}", id);
    var categoryArticlesDto = categoryService.getArticles(new CategoryArticlesRequest(id));
    return ResponseEntity.ok(categoryArticlesDto);
  }
}
