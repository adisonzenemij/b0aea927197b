package code.service;

import code.storage.entity.TypeProcessor;
import code.storage.page.TypeProcessorPage;
import code.storage.repository.TypeProcessorRepository;
import code.web.dto.TypeProcessorDto;
import code.web.mapper.TypeProcessorMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla type_processor. */
@Service
@RequiredArgsConstructor
public class TypeProcessorService {
  public static final String CACHE = "type-processor";
  private final TypeProcessorRepository repository;
  private final TypeProcessorPage page;
  private final TypeProcessorMapper mapper;

  /** Obtiene todos los registros como entidad. */
  @Cacheable(value = CACHE + "-entity-all")
  public List<TypeProcessor> entSelectAll() {
    return repository.findAll();
  }

  /** Obtiene todos los registros como DTO. */
  @Cacheable(value = CACHE + "-dto-all")
  public List<TypeProcessorDto> dtoSelectAll() {
    return entSelectAll().stream().map(mapper::toDto).toList();
  }

  /** Obtiene una entidad por llave primaria. */
  @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
  public TypeProcessor entSelectReg(Long idRegister) {
    return repository.findById(idRegister).orElse(null);
  }

  /** Obtiene un DTO por llave primaria. */
  @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
  public TypeProcessorDto dtoSelectReg(Long idRegister) {
    TypeProcessor entity = entSelectReg(idRegister);
    return entity == null ? null : mapper.toDto(entity);
  }

  /** Obtiene entidades paginadas usando TypeProcessorPage. */
  @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
  public Page<TypeProcessor> entPageAll(int sheet, int row) {
    Pageable pageable = PageRequest.of(sheet, row);
    return page.findBy(pageable);
  }

  /** Obtiene DTO paginados usando TypeProcessorPage. */
  @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
  public Page<TypeProcessorDto> dtoPageAll(int sheet, int row) {
    return entPageAll(sheet, row).map(mapper::toDto);
  }

  /** Verifica la existencia del registro. */
  public boolean existsById(Long idRegister) {
    return repository.existsById(idRegister);
  }

  /** Guarda la entidad y limpia las lecturas cacheadas. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public TypeProcessor entSaveData(TypeProcessor entity) {
    return repository.save(entity);
  }

  /** Guarda los datos recibidos en DTO. */
  @Transactional
  public TypeProcessorDto dtoSaveData(TypeProcessorDto dto) {
    return mapper.toDto(entSaveData(mapper.toEntity(dto)));
  }

  /** Actualiza el registro indicado con los datos DTO. */
  @Transactional
  public TypeProcessorDto dtoUpdateReg(Long idRegister, TypeProcessorDto dto) {
    if (!existsById(idRegister)) return null;
    TypeProcessor entity = mapper.toEntity(dto);
    entity.setIdRegister(idRegister);
    return mapper.toDto(entSaveData(entity));
  }

  /** Elimina la entidad indicada. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public void entDeleteReg(Long idRegister) {
    if (existsById(idRegister)) repository.deleteById(idRegister);
  }

  /** Elimina el registro solicitado por DTO. */
  @Transactional
  public void dtoDeleteReg(Long idRegister) {
    entDeleteReg(idRegister);
  }
}
