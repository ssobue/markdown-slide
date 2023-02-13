package dev.sobue.slide.cache;

import dev.sobue.slide.entity.DocumentKey;
import dev.sobue.slide.entity.MarkdownDocument;
import java.util.List;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for documents.
 *
 * @author SOBUE Sho
 */
@Repository
public interface DocumentRepository extends JpaRepository<MarkdownDocument, DocumentKey> {

  /**
   * Get documents by title name.
   *
   * @param title title name
   * @return Markdown documents
   */
  List<MarkdownDocument> findByDocumentKey_TitleOrderByDocumentKey_PageAsc(@NonNull final String title);
}
