package code.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/** Credenciales requeridas para solicitar un token JWT. */
@Data
public class LoginDto {
    /** Nombre de inicio de sesión registrado en la tabla user_data. */
    @NotBlank private String fdLogin;

    /** Contraseña del usuario; se utiliza exclusivamente para autenticación. */
    @NotBlank private String fdPassd;
}
