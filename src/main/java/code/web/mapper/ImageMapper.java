package code.web.mapper;

import code.storage.entity.Device;
import code.storage.entity.Image;
import code.web.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre Image y su DTO, exponiendo la FK de dispositivo como
 * identificador.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(source = "deviceId", target = "device")
    Image toEntity(ImageDto dto);

    @Mapping(source = "device.idRegister", target = "deviceId")
    ImageDto toDto(Image entity);

    /** Construye una referencia de Device usando la llave foránea suministrada. */
    default Device map(Long id) {
        if (id == null)
            return null;
        Device entity = new Device();
        entity.setIdRegister(id);
        return entity;
    }
}
