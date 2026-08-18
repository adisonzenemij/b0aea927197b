package code.web.mapper;

import code.storage.entity.BrandProcessor;
import code.storage.entity.TypeProcessor;
import code.web.dto.TypeProcessorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre TypeProcessor y su DTO, exponiendo la FK como identificador.
 */
@Mapper(componentModel = "spring")
public interface TypeProcessorMapper {
    @Mapping(source = "brandProcessorId", target = "brandProcessor")
    TypeProcessor toEntity(TypeProcessorDto dto);

    @Mapping(source = "brandProcessor.idRegister", target = "brandProcessorId")
    TypeProcessorDto toDto(TypeProcessor entity);

    /** Construye una referencia JPA sin consultar la tabla relacionada. */
    default BrandProcessor map(Long id) {
        if (id == null)
            return null;
        BrandProcessor entity = new BrandProcessor();
        entity.setIdRegister(id);
        return entity;
    }
}
