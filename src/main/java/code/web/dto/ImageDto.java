package code.web.dto;

import lombok.Data;

/** Datos transferibles de la tabla image. */
@Data
public class ImageDto {
  /** Llave primaria del registro. */
  private Long idRegister;

  /** Imagen o dato codificado en texto. */
  private String fdData;

  /** Llave foránea de device. */
  private Long deviceId;
}
