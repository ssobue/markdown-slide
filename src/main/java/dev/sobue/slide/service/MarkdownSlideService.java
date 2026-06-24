package dev.sobue.slide.service;

import dev.sobue.slide.entity.MarkdownDocument;
import java.io.File;
import java.util.List;

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
  List<MarkdownDocument> get(final File file);

  /**
   * Get a document by file object.
   *
   * @param key key name
   * @param content file content
   * @return parsed Markdown documents
   */
  List<MarkdownDocument> get(final String key, final String content);
}
