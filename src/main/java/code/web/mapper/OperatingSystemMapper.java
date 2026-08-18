package code.web.mapper;

import code.storage.entity.OperatingSystem;
import code.web.dto.OperatingSystemDto;
import org.mapstruct.Mapper;

/** Convierte entre OperatingSystem y su DTO. */
@Mapper(componentModel = "spring")
public interface OperatingSystemMapper {
  OperatingSystem toEntity(OperatingSystemDto dto);

  OperatingSystemDto toDto(OperatingSystem entity);
}
