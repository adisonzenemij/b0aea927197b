package code.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** Datos públicos permitidos para registrar una cuenta con rol Comprador. */
@Data
public class BuyerRegistrationDto {
    /** Correo electrónico de la nueva cuenta. */
    @NotBlank
    @Email
    @Size(max = 150)
    @JsonAlias("fd_email")
    private String fdEmail;

    /** Nombre de inicio de sesión de la nueva cuenta. */
    @NotBlank
    @Size(max = 50)
    @JsonAlias("fd_login")
    private String fdLogin;

    /** Contraseña en texto plano, utilizada solo al recibir la petición. */
    @NotBlank
    @Size(max = 255)
    @JsonAlias("fd_passd")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fdPassd;

    /** Nombres de la persona registrada. */
    @NotBlank
    @Size(max = 255)
    @JsonAlias("fd_name")
    private String fdName;

    /** Apellidos de la persona registrada. */
    @NotBlank
    @Size(max = 255)
    @JsonAlias("fd_srnm")
    private String fdSrnm;
}
