package dev.sobue.slide.service;

import dev.sobue.slide.entity.MarkdownDocument;
import java.io.File;
import java.util.List;
import lombok.NonNull;

/**
 * Interface for markdown-slide logic.
 *
 * @author SOBUE Sho
 */
public interface MarkdownSlideService {

  /**
   * Get a document by file object.
   *
   * @param file markdown file
   * @return parsed Markdown documents
   */
  List<MarkdownDocument> get(@NonNull final File file);

  /**
   * Get a document by file object.
   *
   * @param key key name
   * @param content file content
   * @return parsed Markdown documents
   */
  List<MarkdownDocument> get(@NonNull final String key, @NonNull final String content);
}
