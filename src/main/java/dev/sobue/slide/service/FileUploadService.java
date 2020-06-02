package dev.sobue.slide.service;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

  File upload(String name, MultipartFile multipartFile);
}
