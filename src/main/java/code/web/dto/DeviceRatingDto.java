package code.web.dto;

/** Resumen de calificaciones y distribución de opiniones de un dispositivo. */
public record DeviceRatingDto(
        Double averageRating,
        Long opinionCount,
        Long rating1Count,
        Long rating2Count,
        Long rating3Count,
        Long rating4Count,
        Long rating5Count) {
}
