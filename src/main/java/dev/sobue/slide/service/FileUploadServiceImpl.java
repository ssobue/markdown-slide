package dev.sobue.slide.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService {

  @Override
  public File upload(String name, MultipartFile multipartFile) {
    // ファイルが空の場合は異常終了
    if (multipartFile.isEmpty()) {
      throw new IllegalStateException("invalid file");
    }

    // アップロードファイルを置く
    File uploadFile = new File(name + ".md");

    try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
      StreamUtils.copy(multipartFile.getInputStream(), fos);
      return uploadFile;
    } catch (IOException e) {
      throw new IllegalStateException("fail uploading");
    }
  }
}
