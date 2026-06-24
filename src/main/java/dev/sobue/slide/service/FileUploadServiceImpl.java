package dev.sobue.slide.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import org.springframework.stereotype.Service;

import static org.springframework.util.StreamUtils.copy;

/**
 * Implementation for writing file to local disk.
 *
 * @author SOBUE Sho
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

  /**
   * {@inheritDoc}
   */
  @Override
  public File upload(final String name, final InputStream inputFile) {
    // アップロードファイルを置く
    var uploadFile = MarkdownFileResolver.resolve(name);

    try (var fos = Files.newOutputStream(uploadFile.toPath())) {
      copy(inputFile, fos);
      return uploadFile;
    } catch (IOException _) {
      throw new IllegalStateException("fail writing to local disk");
    }
  }
}
