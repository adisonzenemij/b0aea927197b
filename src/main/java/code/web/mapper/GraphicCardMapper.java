package code.web.mapper;

import code.storage.entity.GraphicCard;
import code.web.dto.GraphicCardDto;
import org.mapstruct.Mapper;

/** Convierte entre GraphicCard y su DTO. */
@Mapper(componentModel = "spring")
public interface GraphicCardMapper {
    GraphicCard toEntity(GraphicCardDto dto);

    GraphicCardDto toDto(GraphicCard entity);
}
