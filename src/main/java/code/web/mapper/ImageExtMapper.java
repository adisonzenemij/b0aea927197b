package code.web.mapper;

import code.storage.entity.ImageExt;
import code.web.dto.ImageExtDto;
import org.mapstruct.Mapper;

/** Convierte entre ImageExt y su DTO. */
@Mapper(componentModel = "spring")
public interface ImageExtMapper {
    ImageExt toEntity(ImageExtDto dto);

    ImageExtDto toDto(ImageExt entity);
}
