package code.storage.page;

import code.storage.entity.OperatingSystem;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla operating_system. */
public interface OperatingSystemPage extends ListPagingAndSortingRepository<OperatingSystem, Long> {
  /** Retorna registros paginados. */
  Page<OperatingSystem> findBy(Pageable pageable);
}
