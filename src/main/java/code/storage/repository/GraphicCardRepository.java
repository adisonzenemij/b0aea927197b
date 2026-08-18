package code.storage.repository;

import code.storage.entity.GraphicCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphicCardRepository extends JpaRepository<GraphicCard, Long> {}
