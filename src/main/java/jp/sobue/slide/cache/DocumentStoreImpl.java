package jp.sobue.slide.cache;

import java.util.List;
import jp.sobue.slide.entity.MarkdownDocument;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

@EnableCaching
@Repository
public class DocumentStoreImpl implements DocumentStore {

  @Override
  @Cacheable(cacheNames = "file",key = "'file-content-' + #key")
  public String put(String key, String content) {
    return content;
  }

  @Override
  @Cacheable(cacheNames = "document",key = "'file-content-' + #key")
  public List<MarkdownDocument> put(String key, List<MarkdownDocument> documents) {
    return documents;
  }
}
