package dev.sobue.slide;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RequiredArgsConstructor
class MarkdownSlideApplicationTests {

  private final ApplicationContext context;

  @Test
  void contextLoads() {
    assertNotNull(context);
  }
}
