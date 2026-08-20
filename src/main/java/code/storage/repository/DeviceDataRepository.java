package code.storage.repository;

import code.storage.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
}
