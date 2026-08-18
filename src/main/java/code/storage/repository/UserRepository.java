package code.storage.repository;

import code.storage.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/** Persistencia de usuarios, incluida la consulta requerida para iniciar sesión. */
public interface UserRepository extends JpaRepository<User, Long> {
  /** Busca un usuario por su nombre de inicio de sesión. */
  @EntityGraph(attributePaths = "role")
  Optional<User> findFirstByFdLogin(String fdLogin);
}
