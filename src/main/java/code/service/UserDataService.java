package code.service;

import code.storage.entity.UserData;
import code.storage.page.UserDataPage;
import code.storage.repository.UserDataRepository;
import code.web.dto.PasswordChangeDto;
import code.web.dto.UserDataDto;
import code.web.mapper.UserDataMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla user_data. */
@Service
@RequiredArgsConstructor
public class UserDataService {
    public static final String CACHE = "user_data";
    private final UserDataRepository repository;
    private final UserDataPage page;
    private final UserDataMapper mapper;
    private final PasswordEncoder passwordEncoder;

    /** Consulta entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<UserData> entSelectAll() {
        return repository.findAll();
    }

    /** Consulta DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<UserDataDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public UserData entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public UserDataDto dtoSelectReg(Long idRegister) {
        UserData e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<UserData> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<UserDataDto> dtoPageAll(int sheet, int row) {
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
    public UserData entSaveData(UserData entity) {
        return repository.save(encodePassword(entity));
    }

    /** Guarda DTO. */
    @Transactional
    public UserDataDto dtoSaveData(UserDataDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public UserDataDto dtoUpdateReg(Long idRegister, UserDataDto dto) {
        UserData current = entSelectReg(idRegister);
        if (current == null)
            return null;
        UserData e = mapper.toEntity(dto);
        e.setIdRegister(idRegister);
        if (dto.getFdPassd() == null || dto.getFdPassd().isBlank())
            e.setFdPassd(current.getFdPassd());
        return mapper.toDto(entSaveData(e));
    }

    /**
     * Actualiza una contraseña cuando los valores nuevo y de confirmación son
     * idénticos. La contraseña se codifica siempre con BCrypt antes de persistir.
     */
    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id",
            CACHE + "-dto-id"}, allEntries = true)
    public void changePassword(Long idRegister, PasswordChangeDto passwordChangeDto) {
        if (!passwordChangeDto.getFdPassdNew().equals(passwordChangeDto.getFdPassdConfirm())) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden");
        }

        UserData user = repository.findById(idRegister)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        user.setFdPassd(passwordEncoder.encode(passwordChangeDto.getFdPassdNew()));
        repository.save(user);
    }

    /** Codifica una contraseña nueva antes de guardarla en la tabla user_data. */
    private UserData encodePassword(UserData entity) {
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
