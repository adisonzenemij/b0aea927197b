package code.storage.repository;

import code.storage.entity.RoleData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDataRepository extends JpaRepository<RoleData, Long> {
}
