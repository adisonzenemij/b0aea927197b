package code.web.mapper;

import code.storage.entity.Role;
import code.web.dto.RoleDto;
import org.mapstruct.Mapper;

/** Convierte entre Role y su DTO. */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto dto);

    RoleDto toDto(Role entity);
}
