package code.service;

import code.storage.entity.UserData;
import code.storage.repository.UserDataRepository;
import code.utility.JwtUtility;
import code.web.dto.JwtTokenDto;
import code.web.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Valida credenciales de la tabla user_data y emite el JWT correspondiente. */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDataRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    /** Inicia sesión con login y contraseña y devuelve un token Bearer vigente. */
    @Transactional(readOnly = true)
    public JwtTokenDto login(LoginDto loginDto) {
        UserData user = userRepository.findFirstByFdLogin(loginDto.getFdLogin()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginDto.getFdPassd(), user.getFdPassd())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
        return jwtUtility.generateToken(user);
    }
}
