package code.web.api;

import code.service.DeviceDataService;
import code.web.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/** API CRUD de la tabla device_data. */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Dispositivos")
public class DeviceDataController {
    private final DeviceDataService service;

    @GetMapping("/dto")
    public ApiResponse<List<DeviceDataDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "InformaciÃƒÂ³n encontrada");
    }

    /** Obtiene DTO paginados a travÃ©s de la clase Page de DeviceData. */
    @GetMapping("/dto/page")
    public ApiResponse<Page<DeviceDataDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "InformaciÃ³n encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<DeviceDataDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "InformaciÃƒÂ³n encontrada");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/dto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<DeviceDataDto>> dtoInsertReg(@RequestBody DeviceDataDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/dto/{idRegister}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<DeviceDataDto> dtoUpdateReg(
            @PathVariable Long idRegister, @RequestBody DeviceDataDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
