package code.web.api;

import code.service.UserService;
import code.web.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/** API CRUD de la tabla user. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
  private final UserService service;

  @GetMapping("/dto")
  public ApiResponse<List<UserDto>> dtoSelectAll() {
    return ApiResponse.success(service.dtoSelectAll(), "InformaciÃƒÂ³n encontrada");
  }

  /** Obtiene DTO paginados a travÃ©s de la clase Page de User. */
  @GetMapping("/dto/page")
  public ApiResponse<Page<UserDto>> dtoPageAll(
      @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
    return ApiResponse.success(service.dtoPageAll(sheet, row), "InformaciÃ³n encontrada");
  }

  @GetMapping("/dto/{idRegister}")
  public ApiResponse<UserDto> dtoSelectReg(@PathVariable Long idRegister) {
    return ApiResponse.success(service.dtoSelectReg(idRegister), "InformaciÃƒÂ³n encontrada");
  }

  @PostMapping("/dto")
  public ResponseEntity<ApiResponse<UserDto>> dtoInsertReg(@RequestBody UserDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
  }

  @PutMapping("/dto/{idRegister}")
  public ApiResponse<UserDto> dtoUpdateReg(
      @PathVariable Long idRegister, @RequestBody UserDto dto) {
    return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
  }

  @DeleteMapping("/dto/{idRegister}")
  public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
    service.dtoDeleteReg(idRegister);
    return ResponseEntity.noContent().build();
  }
}
