package dev.sobue.slide.service;

import dev.sobue.slide.cache.DocumentRepository;
import dev.sobue.slide.converter.Markdown2HtmlConverter;
import dev.sobue.slide.entity.DocumentKey;
import dev.sobue.slide.entity.MarkdownDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation for markdown-slide logic.
 *
 * @author SOBUE Sho
 */
@Service
@RequiredArgsConstructor
public class MarkdownSlideServiceImpl implements MarkdownSlideService {

  /**
   * Constant: Line separator string.
   */
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  /**
   * Converter: HTML from Markdown.
   */
  private final Markdown2HtmlConverter converter;

  /**
   * Repository for documents.
   */
  private final DocumentRepository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  @Cacheable(cacheNames = "file", key = "'file-content-' + #file.name")
  public List<MarkdownDocument> get(@NonNull final File file) {
    var builder = new StringBuilder();
    try (var reader = new BufferedReader(new FileReader(file))) {
      String buf;
      while ((buf = reader.readLine()) != null) {
        builder.append(buf).append(LINE_SEPARATOR);
      }
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }

    var content = builder.toString();
    return get(file.getName(), content);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  @Cacheable(cacheNames = "document", key = "'file-content-' + #key")
  public List<MarkdownDocument> get(@NonNull final String key, @NonNull final String content) {
    var cacheResult = repository.findByDocumentKey_TitleOrderByDocumentKey_PageAsc(key);
    if (!isEmpty(cacheResult)) {
      return cacheResult;
    }

    var chapters = List.of(content.split("\n# "));
    var documents = new ArrayList<MarkdownDocument>();

    for (var i = 0; i < chapters.size(); i++) {
      var document = new MarkdownDocument();
      var documentKey = new DocumentKey();
      documentKey.setTitle(key);
      documentKey.setPage(i + 1);
      document.setDocumentKey(documentKey);
      document.setBody(converter.convert2html((i == 0) ? chapters.get(i) : "# " + chapters.get(i)));
      repository.save(document);
      documents.add(document);
    }

    return documents;
  }
}
