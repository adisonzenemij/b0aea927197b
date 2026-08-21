package code.web.api;

import code.service.AuthService;
import code.web.dto.ApiResponse;
import code.web.dto.BuyerRegistrationDto;
import code.web.dto.JwtTokenDto;
import code.web.dto.LoginDto;
import code.web.dto.RefreshTokenDto;
import code.web.dto.UserDataDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    /** Renueva un JWT vigente sin solicitar nuevamente usuario y contraseña. */
    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<JwtTokenDto> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return ApiResponse.success(service.refreshToken(refreshTokenDto), "Token renovado");
    }

    /** Registra públicamente una cuenta y le asigna exclusivamente el rol Comprador. */
    @PostMapping(value = "/register/comprador", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDataDto>> registerBuyer(
            @Valid @RequestBody BuyerRegistrationDto registrationDto) {
        UserDataDto registeredBuyer = service.registerBuyer(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(registeredBuyer, "Comprador registrado"));
    }
}
