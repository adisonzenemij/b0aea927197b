package code.service;

import code.storage.repository.BrandDeviceRepository;
import code.storage.repository.BrandProcessorRepository;
import code.storage.repository.CommentRepository;
import code.storage.repository.DeviceDataRepository;
import code.storage.repository.DeviceImageRepository;
import code.storage.repository.GraphicCardRepository;
import code.storage.repository.ImageExtRepository;
import code.storage.repository.OperatingSystemRepository;
import code.storage.repository.RoleDataRepository;
import code.storage.repository.TypeProcessorRepository;
import code.storage.repository.UserDataRepository;
import code.web.dto.DashModuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Obtiene los conteos de registros requeridos por el panel de control. */
@Service
@RequiredArgsConstructor
public class DashModuleService {
    private final BrandDeviceRepository brandDeviceRepository;
    private final BrandProcessorRepository brandProcessorRepository;
    private final TypeProcessorRepository typeProcessorRepository;
    private final GraphicCardRepository graphicCardRepository;
    private final OperatingSystemRepository operatingSystemRepository;
    private final DeviceDataRepository deviceDataRepository;
    private final ImageExtRepository imageExtRepository;
    private final DeviceImageRepository deviceImageRepository;
    private final RoleDataRepository roleDataRepository;
    private final UserDataRepository userDataRepository;
    private final CommentRepository commentRepository;

    /** Devuelve una única estructura con el total de cada módulo. */
    @Transactional(readOnly = true)
    public DashModuleDto countModules() {
        return new DashModuleDto(
                brandDeviceRepository.count(),
                brandProcessorRepository.count(),
                typeProcessorRepository.count(),
                graphicCardRepository.count(),
                operatingSystemRepository.count(),
                deviceDataRepository.count(),
                imageExtRepository.count(),
                deviceImageRepository.count(),
                roleDataRepository.count(),
                userDataRepository.count(),
                commentRepository.count());
    }
}
