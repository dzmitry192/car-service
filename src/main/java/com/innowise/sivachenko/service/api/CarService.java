package com.innowise.sivachenko.service.api;

import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.model.exception.BadArgumentException;
import com.innowise.sivachenko.model.exception.CannotDeleteCarException;
import com.innowise.sivachenko.model.exception.CarAlreadyInUseException;
import com.innowise.sivachenko.model.exception.ServiceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.MethodArgumentNotValidException;


public interface CarService {
    Page<CarDto> getCars(
            String name,
            Integer yearOfCar,
            Integer horsepower,
            Float engineCapacity,
            Short seats,
            String color,
            String carMake,
            Long priceDay,
            EngineType engineType,
            CarBodyType bodyType,
            TransmissionType transmissionType,
            Pageable pageable
    ) throws MethodArgumentNotValidException, BadArgumentException;

    CarDto getCarById(Long id) throws EntityNotFoundException;

    CarDto createCar(CreateCarDto createCarDto);

    CarDto updateCar(Long id, UpdateCarDto updateCarDto) throws EntityNotFoundException;

    CarDto updateCarRenter(Long carId, Long clientId, Boolean internal) throws EntityNotFoundException, CarAlreadyInUseException;

    CarDto deleteCar(Long id) throws CannotDeleteCarException, BadArgumentException, ServiceNotFoundException;
}
