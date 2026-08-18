package code.storage.page;

import code.storage.entity.TypeProcessor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla type_processor. */
public interface TypeProcessorPage extends ListPagingAndSortingRepository<TypeProcessor, Long> {
    /** Retorna registros paginados. */
    Page<TypeProcessor> findBy(Pageable pageable);
}
