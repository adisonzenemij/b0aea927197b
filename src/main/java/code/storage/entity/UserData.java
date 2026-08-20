package code.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_register", nullable = false)
    private Long idRegister;

    @NotBlank @Email @Size(max = 150) @Column(name = "fd_email", nullable = false, length = 150)
    private String fdEmail;

    @NotBlank @Size(max = 50) @Column(name = "fd_login", nullable = false, length = 50)
    private String fdLogin;

    @JsonIgnore
    @NotBlank @Size(max = 255) @Column(name = "fd_passd", nullable = false, length = 255)
    private String fdPassd;

    @NotBlank @Size(max = 255) @Column(name = "fd_name", nullable = false, length = 255)
    private String fdName;

    @NotBlank @Size(max = 255) @Column(name = "fd_srnm", nullable = false, length = 255)
    private String fdSrnm;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_role", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private RoleData role;
}
