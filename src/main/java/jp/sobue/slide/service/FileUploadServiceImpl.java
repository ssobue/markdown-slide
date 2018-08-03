package jp.sobue.slide.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadServiceImpl implements FileUploadService {

  @Override
  public File upload(String name, MultipartFile multipartFile) {
    // ファイルが空の場合は異常終了
    if (multipartFile.isEmpty()) {
      throw new RuntimeException("invalid file");
    }

    try {
      // アップロードファイルを置く
      File uploadFile = new File(name + ".md");
      byte[] bytes = multipartFile.getBytes();
      BufferedOutputStream uploadFileStream =
          new BufferedOutputStream(new FileOutputStream(uploadFile));
      uploadFileStream.write(bytes);
      uploadFileStream.close();
      return uploadFile;
    } catch (Exception e) {
      throw new RuntimeException("fail uploading");
    }
  }
}
