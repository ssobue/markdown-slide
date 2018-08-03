package jp.sobue.slide.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;

public class FileCache {

  private static Map<String, String> objects = new ConcurrentHashMap<>();

  private FileCache() {}

  public static void put(@NonNull String key, String content) {
    objects.put(key, content);
  }

  public static String get(@NonNull String key) {
    return objects.get(key);
  }

  public static void clear() {
    objects.clear();
  }
}
