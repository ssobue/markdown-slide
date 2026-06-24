package dev.sobue.slide.service;

import java.io.File;
import java.io.InputStream;

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
  File upload(final String name, final InputStream inputFile);
}
