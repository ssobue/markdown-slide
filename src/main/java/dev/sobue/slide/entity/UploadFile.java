package dev.sobue.slide.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public final class UploadFile {

  private String fileName;

  private MultipartFile file;
}
