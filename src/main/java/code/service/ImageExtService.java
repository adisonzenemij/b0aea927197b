package code.service;

import code.storage.entity.ImageExt;
import code.storage.page.ImageExtPage;
import code.storage.repository.ImageExtRepository;
import code.web.dto.ImageExtDto;
import code.web.mapper.ImageExtMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Operaciones CRUD documentadas de la tabla image_ext. */
@Service
@RequiredArgsConstructor
public class ImageExtService {
    public static final String CACHE = "image_ext";

    private final ImageExtRepository repository;
    private final ImageExtPage page;
    private final ImageExtMapper mapper;

    @Cacheable(value = CACHE + "-entity-all")
    public List<ImageExt> entSelectAll() {
        return repository.findAll();
    }

    @Cacheable(value = CACHE + "-dto-all")
    public List<ImageExtDto> dtoSelectAll() {
        return entSelectAll().stream().map(mapper::toDto).toList();
    }

    @Cacheable(value = CACHE + "-entity-id", key = "#idRegister")
    public ImageExt entSelectReg(Long idRegister) {
        return repository.findById(idRegister).orElse(null);
    }

    @Cacheable(value = CACHE + "-dto-id", key = "#idRegister")
    public ImageExtDto dtoSelectReg(Long idRegister) {
        ImageExt entity = entSelectReg(idRegister);
        return entity == null ? null : mapper.toDto(entity);
    }

    @Cacheable(value = CACHE + "-entity-page", key = "#sheet + '-' + #row")
    public Page<ImageExt> entPageAll(int sheet, int row) {
        Pageable pageable = PageRequest.of(sheet, row);
        return page.findBy(pageable);
    }

    @Cacheable(value = CACHE + "-dto-page", key = "#sheet + '-' + #row")
    public Page<ImageExtDto> dtoPageAll(int sheet, int row) {
        return entPageAll(sheet, row).map(mapper::toDto);
    }

    public boolean existsById(Long idRegister) {
        return repository.existsById(idRegister);
    }

    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"}, allEntries = true)
    public ImageExt entSaveData(ImageExt entity) {
        return repository.save(entity);
    }

    @Transactional
    public ImageExtDto dtoSaveData(ImageExtDto dto) {
        return mapper.toDto(entSaveData(mapper.toEntity(dto)));
    }

    @Transactional
    public ImageExtDto dtoUpdateReg(Long idRegister, ImageExtDto dto) {
        if (!existsById(idRegister)) {
            return null;
        }
        ImageExt entity = mapper.toEntity(dto);
        entity.setIdRegister(idRegister);
        return mapper.toDto(entSaveData(entity));
    }

    @Transactional
    @CacheEvict(value = {CACHE + "-entity-all", CACHE + "-dto-all", CACHE + "-entity-id", CACHE + "-dto-id"}, allEntries = true)
    public void entDeleteReg(Long idRegister) {
        if (existsById(idRegister)) {
            repository.deleteById(idRegister);
        }
    }

    @Transactional
    public void dtoDeleteReg(Long idRegister) {
        entDeleteReg(idRegister);
    }
}
