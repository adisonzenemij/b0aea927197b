package code.web.mapper;

import code.storage.entity.Comment;
import code.storage.entity.Device;
import code.storage.entity.User;
import code.web.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre Comment y su DTO, exponiendo las llaves foráneas como
 * identificadores.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "deviceId", target = "device")
    Comment toEntity(CommentDto dto);

    @Mapping(source = "user.idRegister", target = "userId")
    @Mapping(source = "device.idRegister", target = "deviceId")
    CommentDto toDto(Comment entity);

    default User mapUser(Long id) {
        User entity = new User();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }

    default Device mapDevice(Long id) {
        Device entity = new Device();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }
}
