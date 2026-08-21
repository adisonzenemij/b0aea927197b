package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "device_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceData implements RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_register", nullable = false)
    private Long idRegister;

    @NotBlank @Size(max = 255) @Column(name = "fd_name", nullable = false, length = 255)
    private String fdName;

    /** Código identificador del dispositivo. */
    @NotBlank
    @Size(max = 50)
    @Column(name = "fd_code", nullable = false, length = 50)
    private String fdCode;

    @Lob
    @Column(name = "fd_detail", columnDefinition = "TEXT")
    private String fdDetail;

    @NotNull @DecimalMin(value = "0.00") @Column(name = "fd_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal fdPrice;

    @NotNull @PositiveOrZero @Column(name = "fd_stock", nullable = false)
    private Integer fdStock;

    @NotNull @Column(name = "fd_release", nullable = false)
    private LocalDate fdRelease;

    @NotNull @PositiveOrZero @Column(name = "fd_ram", nullable = false)
    private Integer fdRam;

    @NotNull @PositiveOrZero @Column(name = "fd_storage", nullable = false)
    private Integer fdStorage;

    @NotNull @DecimalMin(value = "0.0") @Digits(integer = 3, fraction = 1) @Column(name = "fd_screen_size", nullable = false, precision = 4, scale = 1)
    private BigDecimal fdScreenSize;

    @NotBlank @Lob
    @Column(name = "fd_image", nullable = false, columnDefinition = "LONGTEXT")
    private String fdImage;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_graphic_card", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private GraphicCard graphicCard;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_brand_device", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BrandDevice brandDevice;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_processor", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TypeProcessor typeProcessor;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operating_system", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private OperatingSystem operatingSystem;
}
