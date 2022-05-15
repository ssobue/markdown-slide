package dev.sobue.slide.service;

import java.io.File;
import java.io.InputStream;
import lombok.NonNull;

public interface FileUploadService {

  File upload(@NonNull final String name, @NonNull final InputStream inputFile);
}
