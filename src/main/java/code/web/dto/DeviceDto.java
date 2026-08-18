package code.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/** Datos transferibles de la tabla device. */
@Data
public class DeviceDto {
    /** Llave primaria del registro. */
    private Long idRegister;

    /** Nombre del dispositivo. */
    private String fdName;

    /** Descripción; permite nulo. */
    private String fdDetail;

    /** Precio con precisión 12,2. */
    private BigDecimal fdPrice;

    /** Cantidad disponible. */
    private Integer fdStock;

    /** Fecha de lanzamiento. */
    private LocalDate fdRelease;

    /** Memoria RAM. */
    private Integer fdRam;

    /** Capacidad de almacenamiento. */
    private Integer fdStorage;

    /** Tamaño de pantalla con precisión 4,1. */
    private BigDecimal fdScreenSize;

    /** Imagen principal en formato texto. */
    private String fdImage;

    /** Llave foránea de graphic_card. */
    private Long graphicCardId;

    /** Llave foránea de brand_device. */
    private Long brandDeviceId;

    /** Llave foránea de type_processor. */
    private Long typeProcessorId;

    /** Llave foránea de operating_system. */
    private Long operatingSystemId;
}
