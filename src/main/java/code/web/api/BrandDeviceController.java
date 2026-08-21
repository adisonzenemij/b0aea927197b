package code.web.api;

import code.service.BrandDeviceService;
import code.web.dto.ApiResponse;
import code.web.dto.BrandDeviceDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/** API CRUD de la tabla brand_device. */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/brand-device", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Marcas Dispositivos")
public class BrandDeviceController {
    private final BrandDeviceService service;

    @GetMapping("/dto")
    public ApiResponse<List<BrandDeviceDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "Informacion encontrada");
    }

    /** Obtiene DTO paginados a travÃ©s de la clase Page de BrandDevice. */
    @GetMapping("/dto/page")
    public ApiResponse<Page<BrandDeviceDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "Informacion encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<BrandDeviceDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "Informacion encontrada");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/dto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BrandDeviceDto>> dtoInsertReg(@RequestBody BrandDeviceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/dto/{idRegister}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<BrandDeviceDto> dtoUpdateReg(
            @PathVariable Long idRegister, @RequestBody BrandDeviceDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
