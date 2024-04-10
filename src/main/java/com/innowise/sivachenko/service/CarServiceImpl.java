package com.innowise.sivachenko.service;

import com.innowise.sivachenko.mapper.CarMapper;
import com.innowise.sivachenko.mapper.CarMapperImpl;
import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.entity.CarEntity;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.repository.CarRepository;
import com.innowise.sivachenko.service.api.CarService;
import com.innowise.sivachenko.service.specification.CarEntitySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.innowise.sivachenko.service.specification.CarEntitySpecification.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapperImpl carMapper;

    @Override
    public Page<CarDto> getCars(String name, Integer yearOfCar, Integer horsepower, Float engineCapacity, Short seats,
                                String color, String carMake, EngineType engineType, CarBodyType bodyType,
                                TransmissionType transmissionType, Pageable pageable) {
        return carRepository.findAll(Specification.where(
                        CarEntitySpecification.withName(name)
                                .and(withYearOfCar(yearOfCar))
                                .and(withHorsepower(horsepower))
                                .and(withEngineCapacity(engineCapacity))
                                .and(withSeats(seats))
                                .and(withColor(color))
                                .and(withCarMake(carMake))
                                .and(withEngineType(engineType))
                                .and(withBodyType(bodyType))
                                .and(withTransmissionType(transmissionType))
                ), pageable)
                .map(carMapper::carEntityToCarDto);
    }

    @Override
    public CarDto getCarById(Long id) throws EntityNotFoundException {
        Optional<CarEntity> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new EntityNotFoundException(String.format("Car with id {%s} not found", id));
        }

        return carMapper.carEntityToCarDto(optionalCar.get());
    }

    @Override
    public CarDto createCar(CreateCarDto createCarDto) {
        CarEntity car = carMapper.createCarDtoToCarEntity(createCarDto);

        return carMapper.carEntityToCarDto(carRepository.save(car));
    }

    @Override
    public CarDto updateCar(Long id, UpdateCarDto updateCarDto) throws EntityNotFoundException {
        Optional<CarEntity> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new EntityNotFoundException(String.format("Car with id {%s} not found", id));
        }
        CarEntity newCar = carMapper.updateCarDtoToCarEntity(optionalCar.get(), updateCarDto);

        return carMapper.carEntityToCarDto(carRepository.save(newCar));
    }

    @Override
    public CarDto deleteCar(Long id) {
        return null;
    }
}
