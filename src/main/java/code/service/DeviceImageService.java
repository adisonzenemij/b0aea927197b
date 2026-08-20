package code.service;

import code.storage.entity.DeviceImage;
import code.storage.page.DeviceImagePage;
import code.storage.repository.DeviceImageRepository;
import code.web.dto.DeviceImageDto;
import code.web.mapper.DeviceImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla device_image. */
@Service
@RequiredArgsConstructor
public class DeviceImageService {
    public static final String CACHE = "device_image";
    private final DeviceImageRepository repository;
    private final DeviceImagePage page;
    private final DeviceImageMapper mapper;

    /** Consulta entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<DeviceImage> entSelectAll() {
        return repository.findAll();
    }

    /** Consulta DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<DeviceImageDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public DeviceImage entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public DeviceImageDto dtoSelectReg(Long idRegister) {
        DeviceImage e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<DeviceImage> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<DeviceImageDto> dtoPageAll(int sheet, int row) {
        return entPageAll(sheet, row).map(mapper::toDto);
    }

    /** Valida existencia. */
    public boolean existsById(Long idRegister) {
        return repository.existsById(idRegister);
    }

    /** Guarda entidad. */
    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id",
            CACHE + "-dto-id"}, allEntries = true)
    public DeviceImage entSaveData(DeviceImage entity) {
        return repository.save(entity);
    }

    /** Guarda DTO. */
    @Transactional
    public DeviceImageDto dtoSaveData(DeviceImageDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public DeviceImageDto dtoUpdateReg(Long idRegister, DeviceImageDto dto) {
        if (!existsById(idRegister))
            return null;
        DeviceImage e = mapper.toEntity(dto);
        e.setIdRegister(idRegister);
        return mapper.toDto(entSaveData(e));
    }

    /** Elimina entidad. */
    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id",
            CACHE + "-dto-id"}, allEntries = true)
    public void entDeleteReg(Long idRegister) {
        if (existsById(idRegister))
            repository.deleteById(idRegister);
    }

    /** Elimina DTO. */
    @Transactional
    public void dtoDeleteReg(Long idRegister) {
        entDeleteReg(idRegister);
    }
}
