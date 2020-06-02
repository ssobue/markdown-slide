package dev.sobue.slide.service;

import dev.sobue.slide.entity.MarkdownDocument;
import java.io.File;
import java.util.List;

public interface MarkdownSlideService {

  List<MarkdownDocument> get(File file);

  List<MarkdownDocument> get(String key, String content);
}
