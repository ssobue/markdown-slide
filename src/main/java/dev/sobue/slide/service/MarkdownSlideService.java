package dev.sobue.slide.service;

import dev.sobue.slide.entity.MarkdownDocument;
import java.io.File;
import java.util.List;
import lombok.NonNull;

public interface MarkdownSlideService {

  List<MarkdownDocument> get(@NonNull final File file);

  List<MarkdownDocument> get(@NonNull final String key, @NonNull final String content);
}
