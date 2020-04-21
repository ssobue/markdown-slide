package jp.sobue.slide.cache;

import java.util.List;
import jp.sobue.slide.entity.MarkdownDocument;

public interface DocumentStore {

  String put(String key, String content);

  List<MarkdownDocument> put(String key, List<MarkdownDocument> documents);
}
