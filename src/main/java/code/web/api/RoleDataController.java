package code.web.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import code.service.RoleDataService;
import code.web.dto.ApiResponse;
import code.web.dto.RoleDataDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/** API CRUD de la tabla role_data. */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/role-data", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles Datos")
@SecurityRequirement(name = "bearerAuth")
public class RoleDataController {
    private final RoleDataService service;

    @GetMapping("/dto")
    public ApiResponse<List<RoleDataDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "Informacion encontrada");
    }

    /** Obtiene DTO paginados a travÃ©s de la clase Page de RoleData. */
    @GetMapping("/dto/page")
    public ApiResponse<Page<RoleDataDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "Informacion encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<RoleDataDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "Informacion encontrada");
    }

    @PostMapping(value = "/dto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<RoleDataDto>> dtoInsertReg(@RequestBody RoleDataDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @PutMapping(value = "/dto/{idRegister}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<RoleDataDto> dtoUpdateReg(
            @PathVariable Long idRegister, @RequestBody RoleDataDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
