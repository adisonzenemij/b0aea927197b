package code.storage.page;

import code.storage.entity.DeviceData;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla device_data. */
public interface DeviceDataPage extends ListPagingAndSortingRepository<DeviceData, Long> {
    /** Retorna registros paginados. */
    Page<DeviceData> findBy(Pageable pageable);
}
