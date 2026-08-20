package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla device_image. */
@Data
public class DeviceImageDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Imagen o dato codificado en texto. */
    private String fdData;

    /** Llave foránea de device_data. */
    private Long deviceId;

    /** Llave foránea de image_ext. */
    private Long imageExtId;
}
