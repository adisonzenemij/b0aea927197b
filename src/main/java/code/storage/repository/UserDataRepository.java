package code.storage.repository;

import code.storage.entity.UserData;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistencia de usuarios, incluida la consulta requerida para iniciar sesión.
 */
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    /** Busca un usuario por su nombre de inicio de sesión. */
    @EntityGraph(attributePaths = "roleData")
    Optional<UserData> findFirstByFdLogin(String fdLogin);
}
