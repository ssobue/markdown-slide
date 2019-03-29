package jp.sobue.slide.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.sobue.slide.entity.MarkdownDocument;
import lombok.NonNull;

public final class MarkdownDocumentCache {

  private static Map<String, List<MarkdownDocument>> objects = new ConcurrentHashMap<>();

  private MarkdownDocumentCache() {}

  public static void put(@NonNull String key, @NonNull List<MarkdownDocument> documents) {
    objects.put(key, documents);
  }

  public static List<MarkdownDocument> get(@NonNull String key) {
    return objects.get(key);
  }

  public static void clear() {
    objects.clear();
  }
}
