package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla role_data. */
@Data
public class RoleDataDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre del rol. */
    private String fdName;
}
