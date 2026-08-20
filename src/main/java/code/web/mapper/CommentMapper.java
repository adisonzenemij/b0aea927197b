package code.web.mapper;

import code.storage.entity.Comment;
import code.storage.entity.DeviceData;
import code.storage.entity.UserData;
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

    default UserData mapUser(Long id) {
        UserData entity = new UserData();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }

    default DeviceData mapDevice(Long id) {
        DeviceData entity = new DeviceData();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }
}
