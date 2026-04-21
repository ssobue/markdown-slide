package dev.sobue.slide.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUploadServiceImplTests {

  private final FileUploadServiceImpl service = new FileUploadServiceImpl();

  @Test
  void uploadWritesMarkdownFileToDisk() throws IOException {
    var uploaded = MarkdownFileResolver.resolve("file-upload-service-test");
    Files.deleteIfExists(uploaded.toPath());

    try {
      var actual = service.upload(
          "file-upload-service-test",
          new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)));

      assertEquals(uploaded, actual);
      assertTrue(Files.exists(actual.toPath()));
      assertEquals("hello", Files.readString(actual.toPath(), StandardCharsets.UTF_8));
    } finally {
      Files.deleteIfExists(uploaded.toPath());
    }
  }
}
