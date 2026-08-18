package code.service;

import code.storage.entity.Image;
import code.storage.page.ImagePage;
import code.storage.repository.ImageRepository;
import code.web.dto.ImageDto;
import code.web.mapper.ImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla image. */
@Service
@RequiredArgsConstructor
public class ImageService {
  public static final String CACHE = "image";
  private final ImageRepository repository;
  private final ImagePage page;
  private final ImageMapper mapper;

  /** Consulta entidades. */
  @Cacheable(value = CACHE + "-entity-all")
  public List<Image> entSelectAll() {
    return repository.findAll();
  }

  /** Consulta DTO. */
  @Cacheable(value = CACHE + "-dto-all")
  public List<ImageDto> dtoSelectAll() {
    return entSelectAll().stream().map(mapper::toDto).toList();
  }

  /** Busca entidad. */
  @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
  public Image entSelectReg(Long idRegister) {
    return repository.findById(idRegister).orElse(null);
  }

  /** Busca DTO. */
  @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
  public ImageDto dtoSelectReg(Long idRegister) {
    Image e = entSelectReg(idRegister);
    return e == null ? null : mapper.toDto(e);
  }

  /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
  public Page<Image> entPageAll(int sheet, int row) {
    Pageable pageable = PageRequest.of(sheet, row);
    return page.findBy(pageable);
  }

  /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
  public Page<ImageDto> dtoPageAll(int sheet, int row) {
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
  public Image entSaveData(Image entity) {
    return repository.save(entity);
  }

  /** Guarda DTO. */
  @Transactional
  public ImageDto dtoSaveData(ImageDto dto) {
    return mapper.toDto(entSaveData(mapper.toEntity(dto)));
  }

  /** Actualiza DTO. */
  @Transactional
  public ImageDto dtoUpdateReg(Long idRegister, ImageDto dto) {
    if (!existsById(idRegister)) return null;
    Image e = mapper.toEntity(dto);
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
