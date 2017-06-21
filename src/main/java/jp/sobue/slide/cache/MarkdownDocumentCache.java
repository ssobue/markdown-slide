package jp.sobue.slide.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.sobue.slide.entity.MarkdownDocument;

public class MarkdownDocumentCache {

  private static Map<String, List<MarkdownDocument>> objects = new HashMap<>();

  private MarkdownDocumentCache() {
  }

  public static synchronized void put(String key, List<MarkdownDocument> documents) {
    if (key == null) {
      throw new RuntimeException("invalid input");
    }

    objects.put(key, documents);
  }

  public static List<MarkdownDocument> get(String key) {
    if (key == null) {
      throw new RuntimeException("invalid input");
    }

    return objects.get(key);
  }

  public static synchronized void clear() {
    objects.clear();
  }
}
