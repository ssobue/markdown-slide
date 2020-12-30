package dev.sobue.slide;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MarkdownSlideApplicationTests {

  private final ApplicationContext context;

  @Autowired
  MarkdownSlideApplicationTests(ApplicationContext context) {
    this.context = context;
  }

  @Test
  void contextLoads() {
    assertNotNull(context);
  }
}
