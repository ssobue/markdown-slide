package jp.sobue.slide.cache;

import java.util.HashMap;
import java.util.Map;

public class FileCache {

  private static Map<String, String> objects = new HashMap<>();

  private FileCache() {
  }

  public static synchronized void put(String key, String content) {
    if (key == null) {
      throw new RuntimeException("invalid input");
    }

    objects.put(key, content);
  }

  public static String get(String key) {
    if (key == null) {
      throw new RuntimeException("invalid input");
    }

    return objects.get(key);
  }

  public static synchronized void clear() {
    objects.clear();
  }
}
