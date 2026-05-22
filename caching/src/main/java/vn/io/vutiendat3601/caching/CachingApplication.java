package vn.io.vutiendat3601.caching;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.caching.entity.Article;
import vn.io.vutiendat3601.caching.entity.Category;
import vn.io.vutiendat3601.caching.repository.ArticleRepository;
import vn.io.vutiendat3601.caching.repository.CategoryRepository;

@SpringBootApplication
public class CachingApplication {
  public static void main(String[] args) {
    SpringApplication.run(CachingApplication.class, args);
  }

  @EventListener
  void startUp(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var categoryRepository = ctx.getBean(CategoryRepository.class);
    var articleRepository = ctx.getBean(ArticleRepository.class);
    var faker = new Faker();
    var category = Category.builder().name("Sport").build();
    categoryRepository.save(category);

    for (int i = 0; i < 100; i++) {
      var article =
          Article.builder()
              .title(faker.funnyName().name())
              .url(faker.internet().url())
              .content(faker.lorem().characters())
              .category(category)
              .build();
      articleRepository.save(article);
    }
  }
}
