package code.utility;

import code.storage.entity.User;
import code.web.dto.JwtTokenDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Crea y verifica tokens JWT firmados para los usuarios de la aplicación. */
@Component
public class JwtUtility {
  private final String issuer;
  private final long expirationSeconds;
  private final Algorithm algorithm;
  private final JWTVerifier verifier;

  /** Inicializa la firma HMAC y el verificador con las propiedades de ambiente. */
  public JwtUtility(
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiration-seconds}") long expirationSeconds) {
    this.issuer = issuer;
    this.expirationSeconds = expirationSeconds;
    this.algorithm = Algorithm.HMAC256(secret);
    this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
  }

  /** Genera un Bearer token con el login y rol actual del usuario. */
  public JwtTokenDto generateToken(User user) {
    Instant now = Instant.now();
    Instant expiresAt = now.plusSeconds(expirationSeconds);
    String role = user.getRole() == null ? "" : user.getRole().getFdName();
    String token =
        JWT.create()
            .withIssuer(issuer)
            .withSubject(user.getFdLogin())
            .withClaim("role", role)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expiresAt))
            .sign(algorithm);
    return new JwtTokenDto(token, "Bearer", expiresAt);
  }

  /** Devuelve el login contenido en un token válido o null cuando no se puede verificar. */
  public String getLoginIfValid(String token) {
    try {
      return verifier.verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }
}
