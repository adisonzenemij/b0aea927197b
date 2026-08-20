package code.storage.repository;

import code.storage.entity.DeviceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceImageRepository extends JpaRepository<DeviceImage, Long> {
}
