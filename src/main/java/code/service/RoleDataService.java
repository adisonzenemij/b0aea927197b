package code.service;

import code.storage.entity.RoleData;
import code.storage.page.RoleDataPage;
import code.storage.repository.RoleDataRepository;
import code.web.dto.RoleDataDto;
import code.web.mapper.RoleDataMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla role_data. */
@Service
@RequiredArgsConstructor
public class RoleDataService {
    public static final String CACHE = "role_data";
    private final RoleDataRepository repository;
    private final RoleDataPage page;
    private final RoleDataMapper mapper;

    /** Consulta entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<RoleData> entSelectAll() {
        return repository.findAll();
    }

    /** Consulta DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<RoleDataDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public RoleData entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public RoleDataDto dtoSelectReg(Long idRegister) {
        RoleData e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<RoleData> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<RoleDataDto> dtoPageAll(int sheet, int row) {
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
    public RoleData entSaveData(RoleData entity) {
        return repository.save(entity);
    }

    /** Guarda DTO. */
    @Transactional
    public RoleDataDto dtoSaveData(RoleDataDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public RoleDataDto dtoUpdateReg(Long idRegister, RoleDataDto dto) {
        if (!existsById(idRegister))
            return null;
        RoleData e = mapper.toEntity(dto);
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
