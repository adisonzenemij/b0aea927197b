package code.service;

import code.storage.entity.User;
import code.storage.page.UserPage;
import code.storage.repository.UserRepository;
import code.web.dto.UserDto;
import code.web.mapper.UserMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla user. */
@Service
@RequiredArgsConstructor
public class UserService {
    public static final String CACHE = "user";
    private final UserRepository repository;
    private final UserPage page;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    /** Consulta entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<User> entSelectAll() {
        return repository.findAll();
    }

    /** Consulta DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<UserDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public User entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public UserDto dtoSelectReg(Long idRegister) {
        User e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<User> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<UserDto> dtoPageAll(int sheet, int row) {
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
    public User entSaveData(User entity) {
        return repository.save(encodePassword(entity));
    }

    /** Guarda DTO. */
    @Transactional
    public UserDto dtoSaveData(UserDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public UserDto dtoUpdateReg(Long idRegister, UserDto dto) {
        User current = entSelectReg(idRegister);
        if (current == null)
            return null;
        User e = mapper.toEntity(dto);
        e.setIdRegister(idRegister);
        if (dto.getFdPassd() == null || dto.getFdPassd().isBlank())
            e.setFdPassd(current.getFdPassd());
        return mapper.toDto(entSaveData(e));
    }

    /** Codifica una contraseña nueva antes de guardarla en la tabla user. */
    private User encodePassword(User entity) {
        String password = entity.getFdPassd();
        if (password != null && !password.isBlank() && !password.startsWith("$2")) {
            entity.setFdPassd(passwordEncoder.encode(password));
        }
        return entity;
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
