package code.service;

import code.storage.entity.BrandProcessor;
import code.storage.page.BrandProcessorPage;
import code.storage.repository.BrandProcessorRepository;
import code.web.dto.BrandProcessorDto;
import code.web.mapper.BrandProcessorMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD de la tabla brand_processor. */
@Service
@RequiredArgsConstructor
public class BrandProcessorService {
    public static final String CACHE = "brand-processor";
    private final BrandProcessorRepository brandProcessorRepository;
    private final BrandProcessorPage brandProcessorPage;
    private final BrandProcessorMapper brandProcessorMapper;

    /** Consulta todos los registros como entidad. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<BrandProcessor> entSelectAll() {
        return brandProcessorRepository.findAll();
    }

    /** Consulta todos los registros como DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<BrandProcessorDto> dtoSelectAll() {
        return entSelectAll().stream().map(brandProcessorMapper::toDto).toList();
    }

    /** Consulta una entidad por llave primaria. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public BrandProcessor entSelectReg(Long idRegister) {
        return brandProcessorRepository.findById(idRegister).orElse(null);
    }

    /** Consulta un DTO por llave primaria. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public BrandProcessorDto dtoSelectReg(Long idRegister) {
        BrandProcessor entity = entSelectReg(idRegister);
        return entity == null ? null : brandProcessorMapper.toDto(entity);
    }

    /** Obtiene entidades paginadas usando BrandProcessorPage. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<BrandProcessor> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return brandProcessorPage.findBy(pageable);
    }

    /** Obtiene DTO paginados usando BrandProcessorPage. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<BrandProcessorDto> dtoPageAll(int sheet, int row) {
        return entPageAll(sheet, row).map(brandProcessorMapper::toDto);
    }

    /** Verifica la existencia del registro. */
    public boolean existsById(Long idRegister) {
        return brandProcessorRepository.existsById(idRegister);
    }

    /** Inserta o actualiza una entidad y limpia la cachÃƒÂ© asociada. */
    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id",
            CACHE + "-dto-id"}, allEntries = true)
    public BrandProcessor entSaveData(BrandProcessor entity) {
        return brandProcessorRepository.save(entity);
    }

    /** Inserta una entidad a partir del DTO. */
    @Transactional
    public BrandProcessorDto dtoSaveData(BrandProcessorDto dto) {
        return brandProcessorMapper.toDto(entSaveData(brandProcessorMapper.toEntity(dto)));
    }

    /** Actualiza un registro existente a partir del DTO. */
    @Transactional
    public BrandProcessorDto dtoUpdateReg(Long idRegister, BrandProcessorDto dto) {
        if (!existsById(idRegister))
            return null;
        BrandProcessor entity = brandProcessorMapper.toEntity(dto);
        entity.setIdRegister(idRegister);
        return brandProcessorMapper.toDto(entSaveData(entity));
    }

    /** Elimina una entidad por llave primaria. */
    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id",
            CACHE + "-dto-id"}, allEntries = true)
    public void entDeleteReg(Long idRegister) {
        if (existsById(idRegister))
            brandProcessorRepository.deleteById(idRegister);
    }

    /** Elimina un registro solicitado desde un DTO. */
    @Transactional
    public void dtoDeleteReg(Long idRegister) {
        entDeleteReg(idRegister);
    }
}
