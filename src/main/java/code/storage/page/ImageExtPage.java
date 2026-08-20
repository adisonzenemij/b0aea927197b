package code.storage.page;

import code.storage.entity.ImageExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla image_ext. */
public interface ImageExtPage extends ListPagingAndSortingRepository<ImageExt, Long> {
    /** Retorna registros paginados. */
    Page<ImageExt> findBy(Pageable pageable);
}
