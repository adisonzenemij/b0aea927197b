package code.web.mapper;

import code.storage.entity.Role;
import code.storage.entity.User;
import code.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre User y su DTO, exponiendo la FK de rol como identificador.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "roleId", target = "role")
    User toEntity(UserDto dto);

    @Mapping(source = "role.idRegister", target = "roleId")
    UserDto toDto(User entity);

    /** Construye una referencia de Role usando la llave foránea suministrada. */
    default Role map(Long id) {
        if (id == null)
            return null;
        Role entity = new Role();
        entity.setIdRegister(id);
        return entity;
    }
}
