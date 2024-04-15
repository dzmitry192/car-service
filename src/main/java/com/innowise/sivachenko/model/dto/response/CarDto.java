package com.innowise.sivachenko.model.dto.response;

import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;

public record CarDto(
        Long id,
        String name,
        Integer yearOfCar,
        Integer horsepower,
        Float engineCapacity,
        Short seats,
        String color,
        String carMake,
        Long usedByClientId,
        EngineType engineType,
        CarBodyType bodyType,
        TransmissionType transmissionType
) {
}
