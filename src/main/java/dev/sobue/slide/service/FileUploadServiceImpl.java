package dev.sobue.slide.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
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
  public File upload(@NonNull final String name, @NonNull final InputStream inputFile) {
    // アップロードファイルを置く
    var uploadFile = new File(name + ".md");

    try (var fos = new FileOutputStream(uploadFile)) {
      copy(inputFile, fos);
      return uploadFile;
    } catch (IOException e) {
      throw new IllegalStateException("fail writing to local disk");
    }
  }
}
