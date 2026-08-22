package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "device_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceImage implements RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_register", nullable = false)
    private Long idRegister;

    @NotBlank @Lob
    @Column(name = "fd_data", nullable = false, columnDefinition = "LONGTEXT")
    private String fdData;

    /** Posición de la imagen dentro de las imágenes del dispositivo. */
    @NotNull
    @Positive
    @Column(name = "fd_order", nullable = false)
    private Integer fdOrder;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_device", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DeviceData device;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_image_ext", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ImageExt imageExt;
}
