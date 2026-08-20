package code.web.mapper;

import code.storage.entity.RoleData;
import code.storage.entity.UserData;
import code.web.dto.UserDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre UserData y su DTO, exponiendo la FK de rol como identificador.
 */
@Mapper(componentModel = "spring")
public interface UserDataMapper {
    @Mapping(source = "roleId", target = "role")
    UserData toEntity(UserDataDto dto);

    @Mapping(source = "role.idRegister", target = "roleId")
    UserDataDto toDto(UserData entity);

    /** Construye una referencia de RoleData usando la llave foránea suministrada. */
    default RoleData map(Long id) {
        if (id == null)
            return null;
        RoleData entity = new RoleData();
        entity.setIdRegister(id);
        return entity;
    }
}
