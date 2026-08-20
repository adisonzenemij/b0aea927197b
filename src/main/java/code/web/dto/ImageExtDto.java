package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla image_ext. */
@Data
public class ImageExtDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Extensión de imagen. */
    private String fdValue;
}
