package dev.sobue.slide.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.stereotype.Service;

import static org.springframework.util.StreamUtils.copy;

@Service
public class FileUploadServiceImpl implements FileUploadService {

  @Override
  public File upload(String name, InputStream inputFile) {
    // アップロードファイルを置く
    File uploadFile = new File(name + ".md");

    try (OutputStream fos = new FileOutputStream(uploadFile)) {
      copy(inputFile, fos);
      return uploadFile;
    } catch (IOException e) {
      throw new IllegalStateException("fail uploading");
    }
  }
}
