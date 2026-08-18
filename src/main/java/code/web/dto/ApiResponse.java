package code.web.dto;

import java.time.Instant;

/** Standard response envelope used by the catalogue API. */
public record ApiResponse<T>(T data, Meta response) {
  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<>(data, new Meta(200, message, "success", Instant.now()));
  }

  public record Meta(int code, String message, String status, Instant timestamp) {}
}
