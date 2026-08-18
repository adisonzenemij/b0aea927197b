package code.storage.page;

import code.storage.entity.Device;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla device. */
public interface DevicePage extends ListPagingAndSortingRepository<Device, Long> {
    /** Retorna registros paginados. */
    Page<Device> findBy(Pageable pageable);
}
