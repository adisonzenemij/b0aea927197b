package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla brand_device. */
@Data
public class BrandDeviceDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre de la marca de dispositivos. */
    private String name;

    /** Imagen de la marca codificada en Base64. */
    private String fdImage;
}
