package com.innowise.sivachenko.model.dto.request;

import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCarDto(
        @NotBlank
        String name,
        @NotNull
        Integer yearOfCar,
        @NotNull
        @Min(1)
        Integer horsepower,
        @NotNull
        Float engineCapacity,
        @NotNull
        @Min(1)
        Short seats,
        @NotBlank
        String color,
        @NotBlank
        String carMake,
        @NotNull
        EngineType engineType,
        @NotNull
        CarBodyType bodyType,
        @NotNull
        TransmissionType transmissionType
) {
}
