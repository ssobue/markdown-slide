package dev.sobue.slide.service;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;

/**
 * Resolves markdown file names to local files safely.
 *
 * @author SOBUE Sho
 */
public final class MarkdownFileResolver {

  private static final Path BASE_DIRECTORY = Path.of("").toAbsolutePath().normalize();
  private static final String MARKDOWN_EXTENSION = ".md";

  private MarkdownFileResolver() {
  }

  /**
   * Resolve a markdown file from a user-provided name.
   *
   * @param name document name without extension
   * @return resolved markdown file
   */
  public static File resolve(@NonNull final String name) {
    var validatedName = validate(name);
    var resolved = BASE_DIRECTORY.resolve(validatedName + MARKDOWN_EXTENSION).normalize();

    if (!resolved.startsWith(BASE_DIRECTORY)) {
      throw new IllegalArgumentException("Invalid file name: " + name);
    }

    return resolved.toFile();
  }

  private static String validate(final String name) {
    if (name.isBlank() || ".".equals(name) || "..".equals(name)) {
      throw new IllegalArgumentException("Invalid file name: " + name);
    }

    if (name.contains("/") || name.contains("\\")) {
      throw new IllegalArgumentException("Invalid file name: " + name);
    }

    try {
      var path = Path.of(name);
      if (path.isAbsolute() || path.getNameCount() != 1) {
        throw new IllegalArgumentException("Invalid file name: " + name);
      }
    } catch (InvalidPathException invalidPathException) {
      throw new IllegalArgumentException("Invalid file name: " + name, invalidPathException);
    }

    return name;
  }
}
