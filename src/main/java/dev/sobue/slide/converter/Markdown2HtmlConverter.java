package dev.sobue.slide.converter;

import lombok.NonNull;

/**
 * Interface for Converter: HTML from Markdown.
 *
 * @author SOBUE Sho
 */
public interface Markdown2HtmlConverter {

  /**
   * Get HTML from Markdown string.
   *
   * @param markdown Markdown string body.
   * @return Converted HTML string.
   */
  String convert2html(@NonNull final String markdown);
}
