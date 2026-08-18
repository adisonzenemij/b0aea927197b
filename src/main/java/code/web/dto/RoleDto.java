package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla role. */
@Data
public class RoleDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre del rol. */
    private String fdName;
}
