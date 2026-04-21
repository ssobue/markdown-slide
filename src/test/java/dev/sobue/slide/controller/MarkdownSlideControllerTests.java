package dev.sobue.slide.controller;

import dev.sobue.slide.entity.DocumentKey;
import dev.sobue.slide.entity.MarkdownDocument;
import dev.sobue.slide.service.FileUploadService;
import dev.sobue.slide.service.MarkdownSlideService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarkdownSlideControllerTests {

  private final FileUploadService fileUploadService = mock(FileUploadService.class);
  private final MarkdownSlideService markdownSlideService = mock(MarkdownSlideService.class);
  private final MarkdownSlideController controller =
      new MarkdownSlideController(fileUploadService, markdownSlideService);

  @Test
  void rootRedirectsToIndex() {
    assertEquals("redirect:/index", controller.root());
  }

  @Test
  void indexAddsTitleToModel() {
    var model = new ExtendedModelMap();

    var actual = controller.index(model);

    assertEquals("index", actual);
    assertEquals("Top Page", model.get("title"));
  }

  @Test
  void uploadAddsTitleToModel() {
    var model = new ExtendedModelMap();

    var actual = controller.upload(model);

    assertEquals("upload", actual);
    assertEquals("Upload Page", model.get("title"));
  }

  @Test
  void viewTopRedirectsToFirstPage() {
    assertEquals("redirect:/view/deck/1", controller.viewTop("deck"));
  }

  @Test
  void uploadPostRedirectsToUploadedFileView() throws IOException {
    var multipartFile = new MockMultipartFile("file", "deck.md", "text/markdown", "# title".getBytes());
    when(fileUploadService.upload(eq("deck"), any(ByteArrayInputStream.class)))
        .thenReturn(new File("deck.md"));

    var actual = controller.uploadPost("deck", multipartFile);

    assertEquals("redirect:/view/deck", actual);
  }

  @Test
  void uploadPostReturnsBadRequestForInvalidFileName() {
    var multipartFile = new MockMultipartFile("file", "deck.md", "text/markdown", "# title".getBytes());
    when(fileUploadService.upload(eq("../deck"), any(ByteArrayInputStream.class)))
        .thenThrow(new IllegalArgumentException("bad name"));

    var actual = assertThrows(ResponseStatusException.class, () -> controller.uploadPost("../deck", multipartFile));

    assertEquals(400, actual.getStatusCode().value());
  }

  @Test
  void viewReturnsSlideBodyAndNavigationForValidInput() {
    when(markdownSlideService.get(any(File.class))).thenReturn(List.of(createDocument("deck", 1, "<p>slide</p>")));
    var model = new ExtendedModelMap();

    var actual = controller.view(model, "deck", 1);

    assertEquals("view", actual);
    assertEquals("/index", model.get("backUrl"));
    assertEquals("/view/deck/2", model.get("forwardUrl"));
    assertEquals("View Page", model.get("title"));
    assertEquals("<p>slide</p>", model.get("body"));
  }

  @Test
  void viewRejectsInvalidFileName() {
    var model = new ExtendedModelMap();

    var actual = assertThrows(ResponseStatusException.class, () -> controller.view(model, "../deck", 1));

    assertEquals(400, actual.getStatusCode().value());
  }

  @Test
  void viewReturnsEndOfPresentationWhenPageExceedsDocumentCount() {
    when(markdownSlideService.get(any(File.class))).thenReturn(List.of(createDocument("deck", 1, "<p>slide</p>")));
    var model = new ExtendedModelMap();

    var actual = controller.view(model, "deck", 2);

    assertEquals("eop", actual);
    assertEquals("/view/deck/1", model.get("backUrl"));
    assertEquals("/view/deck/3", model.get("forwardUrl"));
    assertEquals("End of Presentation", model.get("title"));
  }

  private static MarkdownDocument createDocument(final String title, final int page, final String body) {
    var key = new DocumentKey();
    key.setTitle(title);
    key.setPage(page);

    var document = new MarkdownDocument();
    document.setDocumentKey(key);
    document.setBody(body);
    return document;
  }
}
