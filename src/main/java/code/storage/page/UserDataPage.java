package code.storage.page;

import code.storage.entity.UserData;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla user_data. */
public interface UserDataPage extends ListPagingAndSortingRepository<UserData, Long> {
    /** Retorna registros paginados. */
    Page<UserData> findBy(Pageable pageable);
}
