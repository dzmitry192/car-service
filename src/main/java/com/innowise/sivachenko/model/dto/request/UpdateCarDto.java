package com.innowise.sivachenko.model.dto.request;

import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.validation.api.ValidEngineCapacity;
import com.innowise.sivachenko.validation.api.ValidName;
import com.innowise.sivachenko.validation.api.ValidYear;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateCarDto(
        @ValidName
        String name,
        @ValidYear
        Integer yearOfCar,
        @NotNull @Min(1)
        Integer horsepower,
        @ValidEngineCapacity
        Float engineCapacity,
        @NotNull @Min(1)
        Short seats,
        @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$")
        String color,
        @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$")
        String carMake,
        @NotNull @Min(1)
        Long priceDay,
        @NotNull
        EngineType engineType,
        @NotNull
        CarBodyType bodyType,
        @NotNull
        TransmissionType transmissionType
) {
}
