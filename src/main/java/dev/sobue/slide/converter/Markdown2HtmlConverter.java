package dev.sobue.slide.converter;

import lombok.NonNull;

public interface Markdown2HtmlConverter {

  String convert2html(@NonNull final String markdown);
}
