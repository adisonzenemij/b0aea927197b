package code.storage.page;

import code.storage.entity.BrandProcessor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla brand_processor. */
public interface BrandProcessorPage extends ListPagingAndSortingRepository<BrandProcessor, Long> {
    /** Retorna registros paginados. */
    Page<BrandProcessor> findBy(Pageable pageable);
}
