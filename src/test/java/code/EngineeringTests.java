package code;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EngineeringTests {

  @Autowired private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

  /** Verifica la protección JWT por controlador y por operación CRUD. */
  @Test
  void jwtProtectsConfiguredControllersAndWriteOperations() throws Exception {
    mockMvc.perform(get("/api/roles/dto")).andExpect(status().isUnauthorized());
    mockMvc.perform(get("/api/users/dto")).andExpect(status().isUnauthorized());
    mockMvc.perform(get("/api/comments/dto")).andExpect(status().isOk());
    mockMvc.perform(get("/api/devices/dto")).andExpect(status().isOk());
    mockMvc.perform(post("/api/comments/dto")).andExpect(status().isUnauthorized());
    mockMvc.perform(post("/api/devices/dto")).andExpect(status().isUnauthorized());
  }

}
