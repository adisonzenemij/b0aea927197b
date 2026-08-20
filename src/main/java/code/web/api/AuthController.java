package code.web.api;

import code.service.AuthService;
import code.web.dto.ApiResponse;
import code.web.dto.JwtTokenDto;
import code.web.dto.LoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Punto público de autenticación para obtener el JWT de los recursos
 * protegidos.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Autenticación")
public class AuthController {
    private final AuthService service;

    /**
     * Valida credenciales y retorna el Bearer token que protege roles, usuarios y
     * comentarios.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<JwtTokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ApiResponse.success(service.login(loginDto), "Autenticación exitosa");
    }
}
