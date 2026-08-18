package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla operating_system. */
@Data
public class OperatingSystemDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre del sistema operativo. */
    private String fdName;
}
