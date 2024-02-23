package ru.practicum.ewm.event.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.presentation.dto.UserCreateRequest;
import ru.practicum.ewm.event.presentation.dto.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toUserResponse(UserEntity userEntity);

    UserEntity toUserEntity(UserCreateRequest userCreateRequest);

    List<UserResponse> toUserResponse(List<UserEntity> userEntities);
}

