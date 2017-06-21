package jp.sobue.slide.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.sobue.slide.cache.FileCache;
import jp.sobue.slide.cache.MarkdownDocumentCache;
import jp.sobue.slide.entity.MarkdownDocument;
import org.markdownj.MarkdownProcessor;
import org.springframework.stereotype.Component;

@Component
public class MarkdownSlideServiceImpl implements MarkdownSlideService {

  private static MarkdownProcessor processor = new MarkdownProcessor();

  @Override
  public List<MarkdownDocument> get(File file) {
    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String string = reader.readLine();
      while (string != null){
        builder.append(string + System.getProperty("line.separator"));
        string = reader.readLine();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    String content = builder.toString();
    FileCache.put(file.getName(), content);
    return get(file.getName(),content);
  }

  @Override
  public List<MarkdownDocument> get(String key, String content) {
    List<String> chapters = Arrays.asList(content.split("\n# "));
    List<MarkdownDocument> documents = new ArrayList<>();

    for (int i = 0; i < chapters.size(); i++) {
      MarkdownDocument document = new MarkdownDocument();
      document.setBody(convertHtml((i == 0) ? chapters.get(i) : "# " + chapters.get(i)));
      documents.add(document);
    }

    MarkdownDocumentCache.put(key, documents);

    return documents;
  }

  private String convertHtml(String markdown) {
    return processor.markdown(markdown);
  }
}
