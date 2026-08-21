package code.storage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "graphic_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphicCard implements RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_register", nullable = false)
    private Long idRegister;

    @NotBlank @Size(max = 100) @Column(name = "fd_name", nullable = false, length = 100)
    private String fdName;

    /** Imagen de la tarjeta gráfica codificada en Base64. */
    @NotBlank
    @Lob
    @Column(name = "fd_image", nullable = false, columnDefinition = "LONGTEXT")
    private String fdImage;
}
