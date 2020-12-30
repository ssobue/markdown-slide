package dev.sobue.slide.service;

import java.io.File;
import java.io.InputStream;

public interface FileUploadService {

  File upload(String name, InputStream inputFile);
}
