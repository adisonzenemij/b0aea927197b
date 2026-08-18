package code.utility;

import code.storage.entity.User;
import code.storage.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Lee el encabezado Bearer y registra la autenticación JWT para la solicitud actual. */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtility jwtUtility;
  private final UserRepository userRepository;

  /** Verifica el token, recupera el usuario vigente y crea su contexto de seguridad. */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");
    if (authorization != null
        && authorization.startsWith("Bearer ")
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      String login = jwtUtility.getLoginIfValid(authorization.substring(7));
      if (login != null) userRepository.findFirstByFdLogin(login).ifPresent(this::authenticate);
    }
    filterChain.doFilter(request, response);
  }

  /** Registra las autoridades derivadas del rol actual almacenado en la base de datos. */
  private void authenticate(User user) {
    String role = user.getRole() == null ? "" : user.getRole().getFdName();
    UserDetails principal =
        org.springframework.security.core.userdetails.User.withUsername(user.getFdLogin())
            .password("")
            .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + role)))
            .build();
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
