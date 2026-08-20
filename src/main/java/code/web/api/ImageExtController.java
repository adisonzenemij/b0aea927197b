package code.web.api;

import code.service.ImageExtService;
import code.web.dto.ApiResponse;
import code.web.dto.ImageExtDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

/** API CRUD de la tabla image_ext. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image-exts")
@Tag(name = "Extensiones de imagen")
public class ImageExtController {
    private final ImageExtService service;

    @GetMapping("/dto")
    public ApiResponse<List<ImageExtDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "Información encontrada");
    }

    @GetMapping("/dto/page")
    public ApiResponse<Page<ImageExtDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet,
            @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "Información encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<ImageExtDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "Información encontrada");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/dto")
    public ResponseEntity<ApiResponse<ImageExtDto>> dtoInsertReg(@RequestBody ImageExtDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/dto/{idRegister}")
    public ApiResponse<ImageExtDto> dtoUpdateReg(
            @PathVariable Long idRegister,
            @RequestBody ImageExtDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
