package com.innowise.sivachenko.service;

import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.service.api.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    @Override
    public Page<CarDto> getCars(String name, Integer yearOfCar, Integer horsepower, Float engineCapacity, Short seats,
                                String color, String carMake, EngineType engineType, CarBodyType bodyType,
                                TransmissionType transmissionType, Pageable pageable) {
        return null;
    }

    @Override
    public CarDto getCarById(Long id) {
        return null;
    }

    @Override
    public CarDto createCar(CreateCarDto createCarDto) {
        return null;
    }

    @Override
    public CarDto updateCar(Long id, UpdateCarDto updateCarDto) {
        return null;
    }

    @Override
    public CarDto deleteCar(Long id) {
        return null;
    }
}
