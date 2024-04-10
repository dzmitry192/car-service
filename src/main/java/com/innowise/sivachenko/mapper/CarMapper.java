package com.innowise.sivachenko.mapper;

import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarMapper {

    CarDto carEntityToCarDto(CarEntity carEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usedByClientId", ignore = true)
    CarEntity createCarDtoToCarEntity(CreateCarDto createCarDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usedByClientId", ignore = true)
    CarEntity updateCarDtoToCarEntity(@MappingTarget CarEntity carEntity, UpdateCarDto updateCarDto);
}
