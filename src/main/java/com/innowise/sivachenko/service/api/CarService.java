package com.innowise.sivachenko.service.api;

import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Page<CarDto> getCars(
            String name,
            Integer yearOfCar,
            Integer horsepower,
            Float engineCapacity,
            Short seats,
            String color,
            String carMake,
            EngineType engineType,
            CarBodyType bodyType,
            TransmissionType transmissionType,
            Pageable pageable
    );

    CarDto getCarById(Long id);

    CarDto createCar(CreateCarDto createCarDto);

    CarDto updateCar(Long id, UpdateCarDto updateCarDto);

    CarDto deleteCar(Long id);
}
