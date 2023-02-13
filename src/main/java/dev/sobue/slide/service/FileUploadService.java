package dev.sobue.slide.service;

import java.io.File;
import java.io.InputStream;
import lombok.NonNull;

/**
 * Interface for file uploading.
 *
 * @author SOBUE Sho
 */
public interface FileUploadService {

  /**
   * Upload file.
   *
   * @param name File name
   * @param inputFile Stream of file.
   * @return Reference uploaded file.
   */
  File upload(@NonNull final String name, @NonNull final InputStream inputFile);
}
