package code.service;

import code.storage.entity.RoleData;
import code.storage.entity.UserData;
import code.storage.repository.RoleDataRepository;
import code.storage.repository.UserDataRepository;
import code.web.dto.BuyerRegistrationDto;
import code.utility.JwtUtility;
import code.web.dto.JwtTokenDto;
import code.web.dto.LoginDto;
import code.web.dto.RefreshTokenDto;
import code.web.dto.UserDataDto;
import code.web.mapper.UserDataMapper;
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
    private static final String BUYER_ROLE_NAME = "Comprador";

    private final UserDataRepository userRepository;
    private final RoleDataRepository roleRepository;
    private final UserDataService userDataService;
    private final UserDataMapper userDataMapper;
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

    /**
     * Genera un nuevo JWT a partir de un token actual que conserve firma y
     * vigencia válidas.
     */
    @Transactional(readOnly = true)
    public JwtTokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String login = jwtUtility.getLoginIfValid(refreshTokenDto.getToken());
        if (login == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o vencido");
        }

        UserData user = userRepository.findFirstByFdLogin(login)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Usuario no encontrado"));
        return jwtUtility.generateToken(user);
    }

    /**
     * Registra una cuenta pública con el único rol permitido: Comprador.
     * Nunca acepta un identificador o nombre de rol desde el cliente.
     */
    @Transactional
    public UserDataDto registerBuyer(BuyerRegistrationDto registrationDto) {
        if (userRepository.findFirstByFdLogin(registrationDto.getFdLogin()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El login ya está registrado");
        }
        if (userRepository.findFirstByFdEmail(registrationDto.getFdEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        RoleData buyerRole = roleRepository.findFirstByFdNameIgnoreCase(BUYER_ROLE_NAME)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "El rol Comprador no está configurado"));

        UserData buyer = new UserData();
        buyer.setFdEmail(registrationDto.getFdEmail());
        buyer.setFdLogin(registrationDto.getFdLogin());
        buyer.setFdPassd(registrationDto.getFdPassd());
        buyer.setFdName(registrationDto.getFdName());
        buyer.setFdSrnm(registrationDto.getFdSrnm());
        buyer.setRoleData(buyerRole);

        UserData registeredBuyer = userDataService.entSaveData(buyer);
        return userDataMapper.toDto(registeredBuyer);
    }
}
