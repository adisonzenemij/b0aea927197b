package code.service;

import code.storage.entity.Comment;
import code.storage.entity.User;
import code.storage.page.CommentPage;
import code.storage.repository.CommentRepository;
import code.storage.repository.UserRepository;
import code.web.dto.CommentDto;
import code.web.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla comment. */
@Service
@RequiredArgsConstructor
public class CommentService {
  public static final String CACHE = "comment";
  private final CommentRepository repository;
  private final CommentPage page;
  private final CommentMapper mapper;
  private final UserRepository userRepository;

  /** Consulta entidades. */
  @Cacheable(value = CACHE + "-entity-all")
  public List<Comment> entSelectAll() {
    return repository.findAll();
  }

  /** Consulta DTO. */
  @Cacheable(value = CACHE + "-dto-all")
  public List<CommentDto> dtoSelectAll() {
    return entSelectAll().stream().map(mapper::toDto).toList();
  }

  /** Busca entidad. */
  @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
  public Comment entSelectReg(Long idRegister) {
    return repository.findById(idRegister).orElse(null);
  }

  /** Busca DTO. */
  @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
  public CommentDto dtoSelectReg(Long idRegister) {
    Comment e = entSelectReg(idRegister);
    return e == null ? null : mapper.toDto(e);
  }

  /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
  public Page<Comment> entPageAll(int sheet, int row) {
    Pageable pageable = PageRequest.of(sheet, row);
    return page.findBy(pageable);
  }

  /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
  @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
  public Page<CommentDto> dtoPageAll(int sheet, int row) {
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
  public Comment entSaveData(Comment entity) {
    return repository.save(entity);
  }

  /** Guarda DTO. */
  @Transactional
  public CommentDto dtoSaveData(CommentDto dto) {
    return mapper.toDto(entSaveData(mapper.toEntity(dto)));
  }

  /** Guarda el comentario asignando el usuario autenticado, no un identificador enviado por cliente. */
  @Transactional
  public CommentDto dtoSaveDataForUser(CommentDto dto, String fdLogin) {
    User user = userRepository.findFirstByFdLogin(fdLogin).orElseThrow();
    dto.setUserId(user.getIdRegister());
    return dtoSaveData(dto);
  }

  /** Actualiza DTO. */
  @Transactional
  public CommentDto dtoUpdateReg(Long idRegister, CommentDto dto) {
    Comment current = entSelectReg(idRegister);
    if (current == null) return null;
    Comment e = mapper.toEntity(dto);
    e.setIdRegister(idRegister);
    e.setUser(current.getUser());
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
