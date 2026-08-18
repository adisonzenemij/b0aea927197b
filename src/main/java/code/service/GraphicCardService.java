package code.service;

import code.storage.entity.GraphicCard;
import code.storage.page.GraphicCardPage;
import code.storage.repository.GraphicCardRepository;
import code.web.dto.GraphicCardDto;
import code.web.mapper.GraphicCardMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla graphic_card. */
@Service
@RequiredArgsConstructor
public class GraphicCardService {
    public static final String CACHE = "graphic-card";
    private final GraphicCardRepository repository;
    private final GraphicCardPage page;
    private final GraphicCardMapper mapper;

    /** Obtiene entidades. */
    @Cacheable(value = CACHE + "-entity-all")
    public List<GraphicCard> entSelectAll() {
        return repository.findAll();
    }

    /** Obtiene DTO. */
    @Cacheable(value = CACHE + "-dto-all")
    public List<GraphicCardDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    /** Busca entidad. */
    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public GraphicCard entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    /** Busca DTO. */
    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public GraphicCardDto dtoSelectReg(Long idRegister) {
        GraphicCard e = entSelectReg(idRegister);
        return e == null ? null : mapper.toDto(e);
    }

    /** Obtiene entidades paginadas usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<GraphicCard> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    /** Obtiene DTO paginados usando la clase Page propia de la tabla. */
    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<GraphicCardDto> dtoPageAll(int sheet, int row) {
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
    public GraphicCard entSaveData(GraphicCard entity) {
        return repository.save(entity);
    }

    /** Guarda DTO. */
    @Transactional
    public GraphicCardDto dtoSaveData(GraphicCardDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    /** Actualiza DTO. */
    @Transactional
    public GraphicCardDto dtoUpdateReg(Long idRegister, GraphicCardDto dto) {
        if (!existsById(idRegister))
            return null;
        GraphicCard e = mapper.toEntity(dto);
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
