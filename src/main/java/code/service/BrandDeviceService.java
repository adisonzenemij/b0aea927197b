package code.service;

import code.storage.entity.BrandDevice;
import code.storage.page.BrandDevicePage;
import code.storage.repository.BrandDeviceRepository;
import code.web.dto.BrandDeviceDto;
import code.web.mapper.BrandDeviceMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD de la tabla brand_device. */
@Service
@RequiredArgsConstructor
public class BrandDeviceService {
  public static final String CACHE = "brand-device";
  private final BrandDeviceRepository brandDeviceRepository;
  private final BrandDevicePage brandDevicePage;
  private final BrandDeviceMapper brandDeviceMapper;

  /** Consulta todos los registros como entidad. */
  @Cacheable(value = CACHE + "-entity-all")
  public List<BrandDevice> entSelectAll() {
    return brandDeviceRepository.findAll();
  }

  /** Consulta todos los registros como DTO. */
  @Cacheable(value = CACHE + "-dto-all")
  public List<BrandDeviceDto> dtoSelectAll() {
    return entSelectAll().stream().map(brandDeviceMapper::toDto).toList();
  }

  /** Consulta una entidad por llave primaria. */
  @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
  public BrandDevice entSelectReg(Long idRegister) {
    return brandDeviceRepository.findById(idRegister).orElse(null);
  }

  /** Consulta un DTO por llave primaria. */
  @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
  public BrandDeviceDto dtoSelectReg(Long idRegister) {
    BrandDevice entity = entSelectReg(idRegister);
    return entity == null ? null : brandDeviceMapper.toDto(entity);
  }

  /** Obtiene entidades paginadas usando BrandDevicePage. */
  @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
  public Page<BrandDevice> entPageAll(int sheet, int row) {
    Pageable pageable = PageRequest.of(sheet, row);
    return brandDevicePage.findBy(pageable);
  }

  /** Obtiene DTO paginados usando BrandDevicePage. */
  @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
  public Page<BrandDeviceDto> dtoPageAll(int sheet, int row) {
    return entPageAll(sheet, row).map(brandDeviceMapper::toDto);
  }

  /** Verifica la existencia del registro. */
  public boolean existsById(Long idRegister) {
    return brandDeviceRepository.existsById(idRegister);
  }

  /** Inserta o actualiza una entidad y limpia la cachÃƒÂ© asociada. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public BrandDevice entSaveData(BrandDevice entity) {
    return brandDeviceRepository.save(entity);
  }

  /** Inserta una entidad a partir del DTO. */
  @Transactional
  public BrandDeviceDto dtoSaveData(BrandDeviceDto dto) {
    return brandDeviceMapper.toDto(entSaveData(brandDeviceMapper.toEntity(dto)));
  }

  /** Actualiza un registro existente a partir del DTO. */
  @Transactional
  public BrandDeviceDto dtoUpdateReg(Long idRegister, BrandDeviceDto dto) {
    if (!existsById(idRegister)) return null;
    BrandDevice entity = brandDeviceMapper.toEntity(dto);
    entity.setIdRegister(idRegister);
    return brandDeviceMapper.toDto(entSaveData(entity));
  }

  /** Elimina una entidad por llave primaria. */
  @Transactional
  @CacheEvict(
      value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"},
      allEntries = true)
  public void entDeleteReg(Long idRegister) {
    if (existsById(idRegister)) brandDeviceRepository.deleteById(idRegister);
  }

  /** Elimina un registro solicitado desde un DTO. */
  @Transactional
  public void dtoDeleteReg(Long idRegister) {
    entDeleteReg(idRegister);
  }
}
