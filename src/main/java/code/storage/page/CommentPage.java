package code.storage.page;

import code.storage.entity.Comment;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla comment. */
public interface CommentPage extends ListPagingAndSortingRepository<Comment, Long> {
  /** Retorna registros paginados. */
  Page<Comment> findBy(Pageable pageable);
}
