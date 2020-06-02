package dev.sobue.slide.service;

import dev.sobue.slide.cache.DocumentStore;
import dev.sobue.slide.converter.Markdown2HtmlConverter;
import dev.sobue.slide.entity.MarkdownDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkdownSlideServiceImpl implements MarkdownSlideService {

  private final Markdown2HtmlConverter converter;

  private final DocumentStore documentStore;

  @Override
  public List<MarkdownDocument> get(File file) {
    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String string = reader.readLine();
      while (string != null) {
        builder.append(string).append(System.getProperty("line.separator"));
        string = reader.readLine();
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    String content = builder.toString();
    documentStore.put(file.getName(), content);
    return get(file.getName(), content);
  }

  @Override
  public List<MarkdownDocument> get(String key, String content) {
    List<String> chapters = Arrays.asList(content.split("\n# "));
    List<MarkdownDocument> documents = new ArrayList<>();

    for (int i = 0; i < chapters.size(); i++) {
      MarkdownDocument document = new MarkdownDocument();
      document.setBody(converter.convert2html((i == 0) ? chapters.get(i) : "# " + chapters.get(i)));
      documents.add(document);
    }

    documentStore.put(key, documents);

    return documents;
  }
}
