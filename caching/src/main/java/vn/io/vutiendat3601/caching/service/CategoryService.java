package vn.io.vutiendat3601.caching.service;

import vn.io.vutiendat3601.caching.dto.CategoryArticlesDto;
import vn.io.vutiendat3601.caching.dto.CategoryArticlesRequest;
import vn.io.vutiendat3601.caching.exception.CategoryNotFoundException;

public interface CategoryService {
  CategoryArticlesDto getArticles(CategoryArticlesRequest request) throws CategoryNotFoundException;
}
