package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla brand_processor. */
@Data
public class BrandProcessorDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre de la marca del procesador. */
    private String fdName;

    /** Imagen de la marca codificada en Base64. */
    private String fdImage;
}
