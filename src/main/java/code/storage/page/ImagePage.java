package code.storage.page;

import code.storage.entity.Image;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla image. */
public interface ImagePage extends ListPagingAndSortingRepository<Image, Long> {
    /** Retorna registros paginados. */
    Page<Image> findBy(Pageable pageable);
}
