package code.storage.page;

import code.storage.entity.DeviceImage;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla device_image. */
public interface DeviceImagePage extends ListPagingAndSortingRepository<DeviceImage, Long> {
    /** Retorna registros paginados. */
    Page<DeviceImage> findBy(Pageable pageable);
}
