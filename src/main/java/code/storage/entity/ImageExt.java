package code.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image_ext")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageExt implements RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_register", nullable = false)
    private Long idRegister;

    @NotBlank
    @Size(max = 5)
    @Column(name = "fd_value", nullable = false, length = 5)
    private String fdValue;
}
