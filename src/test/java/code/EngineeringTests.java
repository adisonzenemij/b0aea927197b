package code;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import code.storage.entity.RoleData;
import code.storage.entity.UserData;
import code.storage.repository.RoleDataRepository;
import code.storage.repository.UserDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void contextLoads() {
    }

    /** Verifica la protección JWT por controlador y por operación CRUD. */
    @Test
    void jwtProtectsConfiguredControllersAndWriteOperations() throws Exception {
        mockMvc.perform(get("/api/role-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/user-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/comment/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/device-data/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/device-device-image/dto")).andExpect(status().isOk());
        mockMvc.perform(get("/api/image-ext/dto")).andExpect(status().isOk());
        mockMvc.perform(post("/api/comment/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/device-data/dto")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/device-device-image/dto")).andExpect(status().isUnauthorized());
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

}
