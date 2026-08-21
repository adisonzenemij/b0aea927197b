package code.web.dto;

import java.time.Instant;

/** Datos del JWT emitido después de validar las credenciales de un usuario. */
public record JwtTokenDto(String token, String tokenType, Instant issuedAt, Instant expiresAt) {
}
