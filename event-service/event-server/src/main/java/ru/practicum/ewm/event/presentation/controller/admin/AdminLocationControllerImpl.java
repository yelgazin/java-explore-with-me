package ru.practicum.ewm.event.presentation.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.location.LocationService;
import ru.practicum.ewm.event.presentation.dto.LocationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.LocationResponse;
import ru.practicum.ewm.event.presentation.dto.LocationUpdateRequest;
import ru.practicum.ewm.event.presentation.mapper.LocationMapper;

@RestController
@RequiredArgsConstructor
public class AdminLocationControllerImpl implements AdminLocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Override
    public LocationResponse createLocation(LocationCreateRequest createRequest) {
        return locationMapper.toLocationResponse(
                locationService.createLocation(locationMapper.toLocationEntity(createRequest))
        );
    }

    @Override
    public LocationResponse updateLocation(long locationId, LocationUpdateRequest updateRequest) {
        return locationMapper.toLocationResponse(
                locationService.updateLocation(locationId, locationMapper.toLocationUpdateParameters(updateRequest))
        );
    }

    @Override
    public void deleteLocation(long locationId) {
        locationService.deleteLocation(locationId);
    }
}
