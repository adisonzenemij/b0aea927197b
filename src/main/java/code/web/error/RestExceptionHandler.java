package code.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import code.web.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> notFound(EntityNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ApiResponse<>(
                null,
                new ApiResponse.Meta(
                    404, exception.getMessage(), "error", java.time.Instant.now())));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> invalid(IllegalArgumentException exception) {
    return ResponseEntity.badRequest()
        .body(
            new ApiResponse<>(
                null,
                new ApiResponse.Meta(
                    400, exception.getMessage(), "error", java.time.Instant.now())));
  }
}
