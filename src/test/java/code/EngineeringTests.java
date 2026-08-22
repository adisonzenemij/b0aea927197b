package code;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import code.storage.entity.RoleData;
import code.storage.entity.UserData;
import code.storage.repository.RoleDataRepository;
import code.storage.repository.UserDataRepository;
import code.utility.JwtUtility;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EngineeringTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleDataRepository roleRepository;

    @Autowired
    private UserDataRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtility jwtUtility;

    @Test
    void contextLoads() {
    }

    /** Verifica la protección JWT por controlador y por operación CRUD. */
    @Test
    void jwtProtectsConfiguredControllersAndWriteOperations() throws Exception {
        mockMvc.perform(get("/api/role-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/user-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/dash/module")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/comment/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/comment/dto/device/999/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.averageRating").exists())
                .andExpect(jsonPath("$.data.opinionCount").value(0))
                .andExpect(jsonPath("$.data.rating1Count").value(0))
                .andExpect(jsonPath("$.data.rating2Count").value(0))
                .andExpect(jsonPath("$.data.rating3Count").value(0))
                .andExpect(jsonPath("$.data.rating4Count").value(0))
                .andExpect(jsonPath("$.data.rating5Count").value(0));
        mockMvc.perform(get("/api/device-data/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/device-image/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/image-ext/dto")).andExpect(status().isOk());
        mockMvc.perform(post("/api/comment/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/device-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/device-image/dto")).andExpect(status().isUnauthorized());
    }

    @Test
    void publicBuyerRegistrationAssignsOnlyBuyerRoleAndUsesBcryptCostTwelve() throws Exception {
        RoleData buyerRole = new RoleData();
        buyerRole.setFdName("Comprador");
        roleRepository.save(buyerRole);

        mockMvc.perform(
                        post("/api/auth/register/comprador")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {
                                            "fd_email": "buyer@example.com",
                                            "fd_login": "buyer-example",
                                            "fd_passd": "password-segura",
                                            "fd_name": "Buyer",
                                            "fd_srnm": "Example"
                                        }
                                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.fdPassd").doesNotExist());

        UserData buyer = userRepository.findFirstByFdLogin("buyer-example").orElseThrow();
        org.junit.jupiter.api.Assertions.assertEquals("Comprador", buyer.getRoleData().getFdName());
        org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("password-segura", buyer.getFdPassd()));
        org.junit.jupiter.api.Assertions.assertTrue(buyer.getFdPassd().matches("^\\$2[aby]\\$12\\$.*"));
    }

    @Test
    void corsAllowsBothLocalFrontendOrigins() throws Exception {
        mockMvc.perform(
                        options("/api/auth/register/comprador")
                                .header(HttpHeaders.ORIGIN, "http://127.0.0.1:4200")
                                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://127.0.0.1:4200"));

        mockMvc.perform(
                        options("/api/auth/register/comprador")
                                .header(HttpHeaders.ORIGIN, "http://localhost:4200")
                                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200"));
    }

    @Test
    void passwordChangeUpdatesOnlyWhenNewPasswordMatchesConfirmation() throws Exception {
        RoleData administratorRole = new RoleData();
        administratorRole.setFdName("Administrador");
        administratorRole = roleRepository.save(administratorRole);

        UserData user = new UserData();
        user.setFdEmail("password@example.com");
        user.setFdLogin("password-user");
        user.setFdPassd(passwordEncoder.encode("password-anterior"));
        user.setFdName("Password");
        user.setFdSrnm("User");
        user.setRoleData(administratorRole);
        user = userRepository.save(user);

        String authorization = "Bearer " + jwtUtility.generateToken(user).token();
        mockMvc.perform(
                        put("/api/user-data/dto/{idRegister}/password", user.getIdRegister())
                                .header(HttpHeaders.AUTHORIZATION, authorization)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {
                                            "fd_passd_new": "password-nueva",
                                            "fd_passd_confirm": "password-nueva"
                                        }
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());

        UserData updatedUser = userRepository.findById(user.getIdRegister()).orElseThrow();
        org.junit.jupiter.api.Assertions.assertTrue(
                passwordEncoder.matches("password-nueva", updatedUser.getFdPassd()));
        org.junit.jupiter.api.Assertions.assertTrue(updatedUser.getFdPassd().matches("^\\$2[aby]\\$12\\$.*"));

        mockMvc.perform(
                        put("/api/user-data/dto/{idRegister}/password", user.getIdRegister())
                                .header(HttpHeaders.AUTHORIZATION, authorization)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {
                                            "fdPassdNew": "otra-password",
                                            "fdPassdConfirm": "confirmacion-distinta"
                                        }
                                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refreshTokenRenewsAValidJwtWithoutCredentials() throws Exception {
        RoleData buyerRole = new RoleData();
        buyerRole.setFdName("Comprador");
        buyerRole = roleRepository.save(buyerRole);

        UserData buyer = new UserData();
        buyer.setFdEmail("refresh@example.com");
        buyer.setFdLogin("refresh-user");
        buyer.setFdPassd(passwordEncoder.encode("password-segura"));
        buyer.setFdName("Refresh");
        buyer.setFdSrnm("User");
        buyer.setRoleData(buyerRole);
        buyer = userRepository.save(buyer);

        String currentToken = jwtUtility.generateToken(buyer).token();
        mockMvc.perform(
                        post("/api/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {
                                            "token": "%s"
                                        }
                                        """.formatted(currentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.issuedAt").exists())
                .andExpect(jsonPath("$.data.expiresAt").exists());
    }

}
