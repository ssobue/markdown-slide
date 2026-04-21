package dev.sobue.slide.service;

import dev.sobue.slide.cache.DocumentRepository;
import dev.sobue.slide.converter.Markdown2HtmlConverter;
import dev.sobue.slide.entity.DocumentKey;
import dev.sobue.slide.entity.MarkdownDocument;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarkdownSlideServiceImplTests {

  private final Markdown2HtmlConverter converter = mock(Markdown2HtmlConverter.class);
  private final DocumentRepository repository = mock(DocumentRepository.class);
  private final MarkdownSlideServiceImpl service = new MarkdownSlideServiceImpl(converter,
      repository);

  @Test
  void getReturnsCachedDocumentsWhenRepositoryAlreadyContainsThem() {
    var cached = createDocument("deck.md", 1, "<p>cached</p>");
    when(repository.findByDocumentKey_TitleOrderByDocumentKey_PageAsc("deck.md")).thenReturn(
        java.util.List.of(cached));

    var actual = service.get("deck.md", "# ignored");

    assertEquals(java.util.List.of(cached), actual);
    verify(converter, never()).convert2html(any());
    verify(repository, never()).save(any());
  }

  @Test
  void getSplitsMarkdownIntoPagesAndSavesConvertedDocuments() {
    when(repository.findByDocumentKey_TitleOrderByDocumentKey_PageAsc("deck.md")).thenReturn(
        emptyList());
    when(converter.convert2html(any())).thenAnswer(
        invocation -> "html:" + invocation.getArgument(0, String.class));
    when(repository.save(any(MarkdownDocument.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    var actual = service.get("deck.md", "# First\ncontent\n# Second");

    assertEquals(2, actual.size());
    assertEquals("deck.md", actual.get(0).getDocumentKey().getTitle());
    assertEquals(1, actual.get(0).getDocumentKey().getPage());
    assertEquals("html:# First\ncontent", actual.get(0).getBody());
    assertEquals(2, actual.get(1).getDocumentKey().getPage());
    assertEquals("html:# Second", actual.get(1).getBody());
    verify(converter).convert2html("# First\ncontent");
    verify(converter).convert2html("# Second");
    verify(repository).save(actual.get(0));
    verify(repository).save(actual.get(1));
  }

  @Test
  void getReadsMarkdownContentFromFile() throws IOException {
    when(repository.findByDocumentKey_TitleOrderByDocumentKey_PageAsc("slide-test.md")).thenReturn(
        emptyList());
    when(converter.convert2html(any())).thenAnswer(
        invocation -> invocation.getArgument(0, String.class));
    when(repository.save(any(MarkdownDocument.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    var file = Files.createTempFile("slide-test", ".md").toFile();
    var renamed = new java.io.File(file.getParentFile(), "slide-test.md");
    Files.move(file.toPath(), renamed.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

    try {
      Files.writeString(renamed.toPath(), "# Page 1\ntext");

      var actual = service.get(renamed);

      assertEquals(1, actual.size());
      assertEquals("slide-test.md", actual.get(0).getDocumentKey().getTitle());
      assertEquals("# Page 1\ntext" + System.lineSeparator(), actual.get(0).getBody());
    } finally {
      Files.deleteIfExists(renamed.toPath());
    }
  }

  @Test
  void getWrapsMissingFileAsUncheckedIoException() {
    var missing = new java.io.File("does-not-exist.md");

    assertThrows(java.io.UncheckedIOException.class, () -> service.get(missing));
  }

  private static MarkdownDocument createDocument(final String title, final int page,
      final String body) {
    var key = new DocumentKey();
    key.setTitle(title);
    key.setPage(page);

    var document = new MarkdownDocument();
    document.setDocumentKey(key);
    document.setBody(body);
    return document;
  }
}
