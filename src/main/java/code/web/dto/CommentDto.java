package code.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/** Datos transferibles de la tabla comment. */
@Data
public class CommentDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Contenido del comentario. */
    private String fdContent;

    /** Calificación entre uno y cinco. */
    private Integer fdRating;

    /** Fecha del comentario. */
    private LocalDate fdDate;

    /** Hora del comentario. */
    private LocalTime fdHour;

    /** Llave foránea de user. */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    /** Llave foránea de device. */
    private Long deviceId;
}
