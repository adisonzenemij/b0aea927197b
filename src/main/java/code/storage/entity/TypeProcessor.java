package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "type_processor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeProcessor implements RegisterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_register", nullable = false)
  private Long idRegister;

  @NotBlank
  @Size(max = 100)
  @Column(name = "fd_name", nullable = false, length = 100)
  private String fdName;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_brand_processor", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private BrandProcessor brandProcessor;
}
