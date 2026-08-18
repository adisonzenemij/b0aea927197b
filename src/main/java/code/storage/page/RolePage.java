package code.storage.page;

import code.storage.entity.Role;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla role. */
public interface RolePage extends ListPagingAndSortingRepository<Role, Long> {
    /** Retorna registros paginados. */
    Page<Role> findBy(Pageable pageable);
}
