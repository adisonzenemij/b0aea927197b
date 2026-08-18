package code.web.mapper;

import code.storage.entity.BrandProcessor;
import code.web.dto.BrandProcessorDto;
import org.mapstruct.Mapper;

/** Convierte entre BrandProcessor y su DTO. */
@Mapper(componentModel = "spring")
public interface BrandProcessorMapper {
    BrandProcessor toEntity(BrandProcessorDto dto);

    BrandProcessorDto toDto(BrandProcessor entity);
}
