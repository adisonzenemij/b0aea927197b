package code.web.api;

import code.service.BrandProcessorService;
import code.web.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/** API CRUD de la tabla brand_processor. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand-processors")
@Tag(name = "Marcas de procesadores")
public class BrandProcessorController {
    private final BrandProcessorService service;

    @GetMapping("/dto")
    public ApiResponse<List<BrandProcessorDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "InformaciÃƒÂ³n encontrada");
    }

    /** Obtiene DTO paginados a travÃ©s de la clase Page de BrandProcessor. */
    @GetMapping("/dto/page")
    public ApiResponse<Page<BrandProcessorDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "InformaciÃ³n encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<BrandProcessorDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "InformaciÃƒÂ³n encontrada");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/dto")
    public ResponseEntity<ApiResponse<BrandProcessorDto>> dtoInsertReg(
            @RequestBody BrandProcessorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/dto/{idRegister}")
    public ApiResponse<BrandProcessorDto> dtoUpdateReg(
            @PathVariable Long idRegister, @RequestBody BrandProcessorDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
