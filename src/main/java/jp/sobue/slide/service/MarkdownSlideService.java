package jp.sobue.slide.service;

import java.io.File;
import java.util.List;
import jp.sobue.slide.entity.MarkdownDocument;

public interface MarkdownSlideService {

  List<MarkdownDocument> get(File file);

  List<MarkdownDocument> get(String key, String content);
}
