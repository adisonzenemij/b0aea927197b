package code.storage.page;

import code.storage.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla user. */
public interface UserPage extends ListPagingAndSortingRepository<User, Long> {
    /** Retorna registros paginados. */
    Page<User> findBy(Pageable pageable);
}
