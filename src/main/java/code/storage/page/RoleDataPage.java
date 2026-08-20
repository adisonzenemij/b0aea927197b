package code.storage.page;

import code.storage.entity.RoleData;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla role_data. */
public interface RoleDataPage extends ListPagingAndSortingRepository<RoleData, Long> {
    /** Retorna registros paginados. */
    Page<RoleData> findBy(Pageable pageable);
}
