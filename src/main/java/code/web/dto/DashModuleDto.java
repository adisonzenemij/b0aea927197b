package code.web.dto;

/** Conteos de registros disponibles para los módulos del panel de control. */
public record DashModuleDto(
        long brandDevice,
        long brandProcessor,
        long typeProcessor,
        long graphicCard,
        long operatingSystem,
        long deviceData,
        long imageExt,
        long deviceImage,
        long roleData,
        long userData,
        long comment) {
}
