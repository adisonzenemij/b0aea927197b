package code.web.api;

import code.service.DashModuleService;
import code.web.dto.ApiResponse;
import code.web.dto.DashModuleDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API con los conteos consolidados para el panel de control. */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/dash", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Panel de control")
@SecurityRequirement(name = "bearerAuth")
public class DashModuleController {
    private final DashModuleService service;

    /** Obtiene los conteos de todos los módulos en una única petición HTTP. */
    @GetMapping("/module")
    public ApiResponse<DashModuleDto> countModules() {
        return ApiResponse.success(service.countModules(), "Conteos de módulos encontrados");
    }
}
