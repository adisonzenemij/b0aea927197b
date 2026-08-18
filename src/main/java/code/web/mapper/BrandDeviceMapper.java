package code.web.mapper;

import code.storage.entity.BrandDevice;
import code.web.dto.BrandDeviceDto;
import org.mapstruct.Mapper;

/** Convierte entre BrandDevice y su DTO. */
@Mapper(componentModel = "spring")
public interface BrandDeviceMapper {
  BrandDevice toEntity(BrandDeviceDto dto);

  BrandDeviceDto toDto(BrandDevice entity);
}
