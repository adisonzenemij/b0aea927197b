package code.web.mapper;

import code.storage.entity.*;
import code.web.dto.DeviceDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convierte entre DeviceData y su DTO, exponiendo cada relación por su llave
 * foránea.
 */
@Mapper(componentModel = "spring")
public interface DeviceDataMapper {
    @Mapping(source = "graphicCardId", target = "graphicCard")
    @Mapping(source = "brandDeviceId", target = "brandDevice")
    @Mapping(source = "typeProcessorId", target = "typeProcessor")
    @Mapping(source = "operatingSystemId", target = "operatingSystem")
    DeviceData toEntity(DeviceDataDto dto);

    @Mapping(source = "graphicCard.idRegister", target = "graphicCardId")
    @Mapping(source = "brandDevice.idRegister", target = "brandDeviceId")
    @Mapping(source = "typeProcessor.idRegister", target = "typeProcessorId")
    @Mapping(source = "operatingSystem.idRegister", target = "operatingSystemId")
    DeviceDataDto toDto(DeviceData entity);

    default GraphicCard mapGraphicCard(Long id) {
        GraphicCard entity = new GraphicCard();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }

    default BrandDevice mapBrandDevice(Long id) {
        BrandDevice entity = new BrandDevice();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }

    default TypeProcessor mapTypeProcessor(Long id) {
        TypeProcessor entity = new TypeProcessor();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }

    default OperatingSystem mapOperatingSystem(Long id) {
        OperatingSystem entity = new OperatingSystem();
        entity.setIdRegister(id);
        return id == null ? null : entity;
    }
}
