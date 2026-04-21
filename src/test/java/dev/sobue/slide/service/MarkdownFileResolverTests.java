package dev.sobue.slide.service;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarkdownFileResolverTests {

  @Test
  void resolvesSimpleFileNameInProjectDirectory() {
    var expected = Path.of("").toAbsolutePath().normalize().resolve("demo.md").toFile();

    assertEquals(expected, MarkdownFileResolver.resolve("demo"));
  }

  @Test
  void rejectsPathTraversalName() {
    assertThrows(IllegalArgumentException.class, () -> MarkdownFileResolver.resolve("../demo"));
  }

  @Test
  void rejectsDirectorySeparatorInName() {
    assertThrows(IllegalArgumentException.class, () -> MarkdownFileResolver.resolve("nested/demo"));
  }
}
