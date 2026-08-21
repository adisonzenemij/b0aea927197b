package code.storage.repository;

import code.storage.entity.RoleData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDataRepository extends JpaRepository<RoleData, Long> {
    /** Busca un rol por su nombre sin depender de su identificador numérico. */
    Optional<RoleData> findFirstByFdNameIgnoreCase(String fdName);
}
