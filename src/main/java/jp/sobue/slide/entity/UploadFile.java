package jp.sobue.slide.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public final class UploadFile {

  private String fileName;

  private MultipartFile file;
}
