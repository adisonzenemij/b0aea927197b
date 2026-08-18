package code.storage.page;

import code.storage.entity.GraphicCard;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla graphic_card. */
public interface GraphicCardPage extends ListPagingAndSortingRepository<GraphicCard, Long> {
    /** Retorna registros paginados. */
    Page<GraphicCard> findBy(Pageable pageable);
}
