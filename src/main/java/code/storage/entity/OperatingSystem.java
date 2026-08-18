package code.storage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "operating_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingSystem implements RegisterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_register", nullable = false)
  private Long idRegister;

  @NotBlank
  @Size(max = 100)
  @Column(name = "fd_name", nullable = false, length = 100)
  private String fdName;
}
