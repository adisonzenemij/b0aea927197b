package code.web.api;

import code.service.AuthService;
import code.web.dto.ApiResponse;
import code.web.dto.JwtTokenDto;
import code.web.dto.LoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Punto público de autenticación para obtener el JWT de los recursos protegidos. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Autenticación")
public class AuthController {
  private final AuthService service;

  /** Valida credenciales y retorna el Bearer token que protege roles, usuarios y comentarios. */
  @PostMapping("/login")
  public ApiResponse<JwtTokenDto> login(@Valid @RequestBody LoginDto loginDto) {
    return ApiResponse.success(service.login(loginDto), "Autenticación exitosa");
  }
}
