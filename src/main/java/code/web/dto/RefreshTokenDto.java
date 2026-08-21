package code.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/** Token vigente suministrado para solicitar su renovación. */
@Data
public class RefreshTokenDto {
    /** JWT Bearer actual, sin el prefijo Bearer. */
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;
}
