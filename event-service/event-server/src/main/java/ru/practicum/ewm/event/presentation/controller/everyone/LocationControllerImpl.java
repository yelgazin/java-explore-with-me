package ru.practicum.ewm.event.presentation.controller.everyone;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.location.LocationService;
import ru.practicum.ewm.event.presentation.dto.LocationResponse;
import ru.practicum.ewm.event.presentation.mapper.LocationMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Override
    public List<LocationResponse> findLocations(int from, int size) {
        return locationMapper.toLocationResponse(locationService.findLocations(from, size));
    }

    @Override
    public LocationResponse findLocation(long locationId) {
        return locationMapper.toLocationResponse(locationService.findById(locationId));
    }
}
