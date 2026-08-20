package code.web.mapper;

import code.storage.entity.DeviceData;
import code.storage.entity.DeviceImage;
import code.web.dto.DeviceImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre DeviceImage y su DTO, exponiendo la FK de dispositivo como
 * identificador.
 */
@Mapper(componentModel = "spring")
public interface DeviceImageMapper {
    @Mapping(source = "deviceId", target = "device")
    DeviceImage toEntity(DeviceImageDto dto);

    @Mapping(source = "device.idRegister", target = "deviceId")
    DeviceImageDto toDto(DeviceImage entity);

    /** Construye una referencia de DeviceData usando la llave foránea suministrada. */
    default DeviceData map(Long id) {
        if (id == null)
            return null;
        DeviceData entity = new DeviceData();
        entity.setIdRegister(id);
        return entity;
    }
}
