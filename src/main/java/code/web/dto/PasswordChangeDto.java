package code.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** Contraseña nueva y su confirmación para actualizar una cuenta existente. */
@Data
public class PasswordChangeDto {
    /** Nueva contraseña que se almacenará después de validarla. */
    @NotBlank
    @Size(max = 255)
    @JsonAlias("fd_passd_new")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fdPassdNew;

    /** Confirmación de la nueva contraseña. */
    @NotBlank
    @Size(max = 255)
    @JsonAlias("fd_passd_confirm")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fdPassdConfirm;
}
