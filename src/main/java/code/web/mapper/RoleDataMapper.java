package code.web.mapper;

import code.storage.entity.RoleData;
import code.web.dto.RoleDataDto;
import org.mapstruct.Mapper;

/** Convierte entre RoleData y su DTO. */
@Mapper(componentModel = "spring")
public interface RoleDataMapper {
    RoleData toEntity(RoleDataDto dto);

    RoleDataDto toDto(RoleData entity);
}
