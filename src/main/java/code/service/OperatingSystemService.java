package code.service;

import code.storage.entity.OperatingSystem;
import code.storage.page.OperatingSystemPage;
import code.storage.repository.OperatingSystemRepository;
import code.web.dto.OperatingSystemDto;
import code.web.mapper.OperatingSystemMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla operating_system. */
@Service
@RequiredArgsConstructor
public class OperatingSystemService {
  public static final String CACHE = "operating-system";
  private final OperatingSystemRepository repository;
  private final OperatingSystemPage page;
  private final OperatingSystemMapper mapper;

  /** Consulta entidades. */
  @Cacheable(value = CACHE + "-entity-all")
  public List<OperatingSystem> entSelectAll() {
    return repository.findAll();
  }

  /** Consulta DTO. */
  @Cacheable(value = CACHE + "-dto-all")
  public List<OperatingSystemDto> dtoSelectAll() {
    return entSelectAll().stream().map(mapper::toDto).toList();
  }

  /** Busca entidad. */
  @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
  public OperatingSystem entSelectReg(Long idRegister) {
    return repository.findById(idRegister).orElse(null);
  }

  /** Busca DTO. */
  @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
  public OperatingSystemDto dtoSelectReg(Long idRegister) {
    OperatingSystem e = entSelectReg(idRegister);
    return e == null ? null : mapper.toDto(e);
  }

  /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
  public Page<OperatingSystem> entPageAll(int sheet, int row) {
    Pageable pageable = PageRequest.of(sheet, row);
    return page.findBy(pageable);
  }

  /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
  public Page<OperatingSystemDto> dtoPageAll(int sheet, int row) {
    return entPageAll(sheet, row).map(mapper::toDto);
  }

  /** Valida existencia. */
  public boolean existsById(Long idRegister) {
    return repository.existsById(idRegister);
  }

  /** Guarda entidad. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public OperatingSystem entSaveData(OperatingSystem entity) {
    return repository.save(entity);
  }

  /** Guarda DTO. */
  @Transactional
  public OperatingSystemDto dtoSaveData(OperatingSystemDto dto) {
    return mapper.toDto(entSaveData(mapper.toEntity(dto)));
  }

  /** Actualiza DTO. */
  @Transactional
  public OperatingSystemDto dtoUpdateReg(Long idRegister, OperatingSystemDto dto) {
    if (!existsById(idRegister)) return null;
    OperatingSystem e = mapper.toEntity(dto);
    e.setIdRegister(idRegister);
    return mapper.toDto(entSaveData(e));
  }

  /** Elimina entidad. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public void entDeleteReg(Long idRegister) {
    if (existsById(idRegister)) repository.deleteById(idRegister);
  }

  /** Elimina DTO. */
  @Transactional
  public void dtoDeleteReg(Long idRegister) {
    entDeleteReg(idRegister);
  }
}
