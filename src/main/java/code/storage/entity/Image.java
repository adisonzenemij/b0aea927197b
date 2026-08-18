package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image implements RegisterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_register", nullable = false)
  private Long idRegister;

  @NotBlank
  @Lob
  @Column(name = "fd_data", nullable = false, columnDefinition = "TEXT")
  private String fdData;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_device", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Device device;
}
