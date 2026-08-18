package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements RegisterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_register", nullable = false)
  private Long idRegister;

  @NotBlank
  @Lob
  @Column(name = "fd_content", nullable = false, columnDefinition = "TEXT")
  private String fdContent;

  @NotNull
  @Min(1)
  @Max(5)
  @Column(name = "fd_rating", nullable = false)
  private Integer fdRating;

  @NotNull
  @Column(name = "fd_date", nullable = false)
  private LocalDateTime fdDate;

  @NotNull
  @Column(name = "fd_hour", nullable = false)
  private LocalDateTime fdHour;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_user", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_device", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Device device;
}
