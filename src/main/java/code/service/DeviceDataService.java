package code.service;

import code.storage.entity.DeviceData;
import code.storage.page.DeviceDataPage;
import code.storage.repository.DeviceDataRepository;
import code.web.dto.DeviceDataDto;
import code.web.mapper.DeviceDataMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla device_data. */
@Service
@RequiredArgsConstructor
public class DeviceDataService {
    public static final String CACHE = "device_data";
    private final DeviceDataRepository repository;
    private final DeviceDataPage page;
    private final DeviceDataMapper mapper;

    /** Consulta entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<DeviceData> entSelectAll() {
        return repository.findAll();
    }

    /** Consulta DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<DeviceDataDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public DeviceData entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public DeviceDataDto dtoSelectReg(Long idRegister) {
        DeviceData e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<DeviceData> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<DeviceDataDto> dtoPageAll(int sheet, int row) {
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
    public DeviceData entSaveData(DeviceData entity) {
        return repository.save(entity);
    }

    /** Guarda DTO. */
    @Transactional
    public DeviceDataDto dtoSaveData(DeviceDataDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public DeviceDataDto dtoUpdateReg(Long idRegister, DeviceDataDto dto) {
        if (!existsById(idRegister))
            return null;
        DeviceData e = mapper.toEntity(dto);
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
