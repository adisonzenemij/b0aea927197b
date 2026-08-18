package code.storage.page;

import code.storage.entity.BrandDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

/** Paginación de la tabla brand_device. */
public interface BrandDevicePage extends ListPagingAndSortingRepository<BrandDevice, Long> {
    /** Retorna registros paginados y ordenados por Pageable. */
    Page<BrandDevice> findBy(Pageable pageable);
}
