package code.web.api;

import java.util.List;

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

import code.service.ImageService;
import code.web.dto.ApiResponse;
import code.web.dto.ImageDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/** API CRUD de la tabla image. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Tag(name = "Imagenes")
public class ImageController {
    private final ImageService service;

    @GetMapping("/dto")
    public ApiResponse<List<ImageDto>> dtoSelectAll() {
        return ApiResponse.success(service.dtoSelectAll(), "InformaciÃƒÂ³n encontrada");
    }

    /** Obtiene DTO paginados a travÃ©s de la clase Page de Image. */
    @GetMapping("/dto/page")
    public ApiResponse<Page<ImageDto>> dtoPageAll(
            @RequestParam(defaultValue = "0") int sheet, @RequestParam(defaultValue = "50") int row) {
        return ApiResponse.success(service.dtoPageAll(sheet, row), "InformaciÃ³n encontrada");
    }

    @GetMapping("/dto/{idRegister}")
    public ApiResponse<ImageDto> dtoSelectReg(@PathVariable Long idRegister) {
        return ApiResponse.success(service.dtoSelectReg(idRegister), "InformaciÃƒÂ³n encontrada");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/dto")
    public ResponseEntity<ApiResponse<ImageDto>> dtoInsertReg(@RequestBody ImageDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.dtoSaveData(dto), "Registro creado"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/dto/{idRegister}")
    public ApiResponse<ImageDto> dtoUpdateReg(
            @PathVariable Long idRegister, @RequestBody ImageDto dto) {
        return ApiResponse.success(service.dtoUpdateReg(idRegister, dto), "Registro actualizado");
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/dto/{idRegister}")
    public ResponseEntity<Void> dtoDeleteReg(@PathVariable Long idRegister) {
        service.dtoDeleteReg(idRegister);
        return ResponseEntity.noContent().build();
    }
}
