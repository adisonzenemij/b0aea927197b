package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla graphic_card. */
@Data
public class GraphicCardDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre de la tarjeta gráfica. */
    private String fdName;
}
