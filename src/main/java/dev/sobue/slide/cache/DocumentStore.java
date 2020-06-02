package dev.sobue.slide.cache;

import dev.sobue.slide.entity.MarkdownDocument;
import java.util.List;

public interface DocumentStore {

  String put(String key, String content);

  List<MarkdownDocument> put(String key, List<MarkdownDocument> documents);
}
