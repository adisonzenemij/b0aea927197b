package code.storage.repository;

import code.storage.entity.Comment;
import code.web.dto.DeviceRatingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /** Calcula el promedio y el total de opiniones para un dispositivo. */
    @Query(
            """
            select new code.web.dto.DeviceRatingDto(
                coalesce(avg(item.fdRating), 0.0),
                count(item),
                coalesce(sum(case when item.fdRating = 1 then 1 else 0 end), 0),
                coalesce(sum(case when item.fdRating = 2 then 1 else 0 end), 0),
                coalesce(sum(case when item.fdRating = 3 then 1 else 0 end), 0),
                coalesce(sum(case when item.fdRating = 4 then 1 else 0 end), 0),
                coalesce(sum(case when item.fdRating = 5 then 1 else 0 end), 0)
            )
            from Comment item
            where item.device.idRegister = :deviceId
            """)
    DeviceRatingDto findRatingByDeviceId(@Param("deviceId") Long deviceId);
}
