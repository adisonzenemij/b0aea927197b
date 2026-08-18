package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla type_processor. */
@Data
public class TypeProcessorDto {
  /** Llave primaria del registro. */
  private Long idRegister;

  /** Nombre del tipo de procesador. */
  private String fdName;

  /** Llave foránea de brand_processor. */
  private Long brandProcessorId;
}
