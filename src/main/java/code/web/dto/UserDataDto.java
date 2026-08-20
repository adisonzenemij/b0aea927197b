package code.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** Datos transferibles de la tabla user_data. */
@Data
public class UserDataDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Correo electrónico. */
    private String fdEmail;

    /** Nombre de inicio de sesión. */
    private String fdLogin;

    /** Contraseña; solo se recibe, nunca se retorna. */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fdPassd;

    /** Nombres del usuario. */
    private String fdName;

    /** Apellidos del usuario. */
    private String fdSrnm;

    /** Llave foránea de role_data. */
    private Long roleDataId;
}
