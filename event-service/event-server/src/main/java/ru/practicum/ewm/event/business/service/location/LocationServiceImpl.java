package ru.practicum.ewm.event.business.service.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.LocationCopier;
import ru.practicum.ewm.event.business.dto.LocationUpdateParameters;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.LocationEntity;
import ru.practicum.ewm.event.persistence.repository.LocationRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.util.List;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationCopier locationCopier;

    @Override
    public LocationEntity createLocation(LocationEntity location) {
        log.info("Создание локации. {}.", location);

        return locationRepository.save(location);
    }

    @Override
    public LocationEntity updateLocation(long locationId, LocationUpdateParameters updateParameters) {
        log.info("Обновление локации c id {}. Параметры обновления {}.", locationId, updateParameters);

        LocationEntity targetLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(
                                format("Локация с id {} не найдена.", locationId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        locationCopier.update(targetLocation, updateParameters);
        return targetLocation;
    }

    @Override
    public void deleteLocation(long locationId) {
        log.info("Удаление локации c id {}.", locationId);

        if (!locationRepository.existsById(locationId)) {
            throw new NotFoundException(
                    format("Локация с id {} не найдена.", locationId),
                    "Отсутствуют сведения в базе данных."
            );
        }

        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationEntity> findLocations(long from, int size) {
        log.info("Получение локаций начиная с {}, количество объектов {}.", from, size);

        return locationRepository.findAllByOrderById(PageableUtil.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public LocationEntity findById(long locationId) {
        log.info("Получение локации по id {}.", locationId);

        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(
                                format("Локация с id {} не найдена.", locationId),
                                "Отсутствуют сведения в базе данных."
                        )
                );
    }
}
