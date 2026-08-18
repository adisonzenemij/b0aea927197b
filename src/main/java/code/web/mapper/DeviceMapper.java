package code.web.mapper;

import code.storage.entity.*;
import code.web.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Convierte entre Device y su DTO, exponiendo cada relación por su llave foránea. */
@Mapper(componentModel = "spring")
public interface DeviceMapper {
  @Mapping(source = "graphicCardId", target = "graphicCard")
  @Mapping(source = "brandDeviceId", target = "brandDevice")
  @Mapping(source = "typeProcessorId", target = "typeProcessor")
  @Mapping(source = "operatingSystemId", target = "operatingSystem")
  Device toEntity(DeviceDto dto);

  @Mapping(source = "graphicCard.idRegister", target = "graphicCardId")
  @Mapping(source = "brandDevice.idRegister", target = "brandDeviceId")
  @Mapping(source = "typeProcessor.idRegister", target = "typeProcessorId")
  @Mapping(source = "operatingSystem.idRegister", target = "operatingSystemId")
  DeviceDto toDto(Device entity);

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
