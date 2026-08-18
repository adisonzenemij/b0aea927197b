package code.web.api;

import code.service.CommentService;
import code.web.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/** API CRUD de la tabla comment. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "Comentarios")
public class CommentController {
  private final CommentService service;

  @GetMapping("/dto")
  public ApiResponse<List<CommentDto>> dtoSelectAll() {
    return ApiResponse.success(service.dtoSelectAll(), "InformaciÃƒÂ³n encontrada");
  }

  /** Obtiene DTO paginados a travÃ©s de la clase Page de Comment. */
  @GetMapping("/dto/page")
  public ApiResponse<Page<CommentDto>> dtoPageAll(
      @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
    return ApiResponse.success(service.dtoPageAll(sheet, row), "InformaciÃ³n encontrada");
  }

  @GetMapping("/dto/{idRegister}")
  public ApiResponse<CommentDto> dtoSelectReg(@PathVariable Long idRegister) {
    return ApiResponse.success(service.dtoSelectReg(idRegister), "InformaciÃƒÂ³n encontrada");
  }

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping("/dto")
  public ResponseEntity<ApiResponse<CommentDto>> dtoInsertReg(
      @RequestBody CommentDto dto, Authentication authentication) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.success(
                service.dtoSaveDataForUser(dto, authentication.getName()), "Registro creado"));
  }

  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/dto/{idRegister}")
  public ApiResponse<CommentDto> dtoUpdateReg(
      @PathVariable Long idRegister, @RequestBody CommentDto dto) {
    return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
  }

  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/dto/{idRegister}")
  public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
    service.dtoDeleteReg(idRegister);
    return ResponseEntity.noContent().build();
  }
}
