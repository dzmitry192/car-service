package com.innowise.sivachenko.service;

import com.innowise.sivachenko.feign.RentServiceFeignClient;
import com.innowise.sivachenko.mapper.CarMapperImpl;
import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.entity.CarEntity;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.model.exception.BadArgumentException;
import com.innowise.sivachenko.model.exception.CarAlreadyInUseException;
import com.innowise.sivachenko.repository.CarRepository;
import com.innowise.sivachenko.service.api.CarService;
import com.innowise.sivachenko.service.specification.CarEntitySpecification;
import com.innowise.sivachenko.validation.util.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innowise.sivachenko.model.exception.ServiceNotFoundException;

import java.util.Optional;

import static com.innowise.sivachenko.service.specification.CarEntitySpecification.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapperImpl carMapper;
    private final RentServiceFeignClient rentServiceFeignClient;

    @Override
    public Page<CarDto> getCars(String name, Integer yearOfCar, Integer horsepower, Float engineCapacity, Short seats,
                                String color, String carMake, Long priceDay, EngineType engineType, CarBodyType bodyType,
                                TransmissionType transmissionType, Pageable pageable) throws BadArgumentException {
        ValidationUtils.isValidParams(name, yearOfCar, horsepower, engineCapacity, seats, color, carMake, priceDay);
        return carRepository.findAll(Specification.where(
                        CarEntitySpecification.withName(name)
                                .and(withYearOfCar(yearOfCar))
                                .and(withHorsepower(horsepower))
                                .and(withEngineCapacity(engineCapacity))
                                .and(withSeats(seats))
                                .and(withColor(color))
                                .and(withCarMake(carMake))
                                .and(withPriceDay(priceDay))
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
    @Transactional
    public CarDto updateCar(Long id, UpdateCarDto updateCarDto) throws EntityNotFoundException {
        Optional<CarEntity> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new EntityNotFoundException(String.format("Car with id {%s} not found", id));
        }
        CarEntity newCar = carMapper.updateCarDtoToCarEntity(optionalCar.get(), updateCarDto);

        return carMapper.carEntityToCarDto(carRepository.save(newCar));
    }

    @Override
    @Transactional
    public CarDto updateCarRenter(Long carId, Long clientId, Boolean internal) throws EntityNotFoundException, CarAlreadyInUseException {
        Optional<CarEntity> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            throw new EntityNotFoundException(String.format("Car with id {%s} not found", carId));
        }
        CarEntity car = optionalCar.get();
        if (internal) {
            car.setUsedByClientId(null);
        } else {
            if (optionalCar.get().getUsedByClientId() != null) {
                throw new CarAlreadyInUseException(String.format("Car with id {%s} is already in use", carId));
            }
            car.setUsedByClientId(clientId);
        }
        return carMapper.carEntityToCarDto(carRepository.save(car));
    }

    @Override
    public CarDto deleteCar(Long carId) throws BadArgumentException, ServiceNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new EntityNotFoundException(String.format("Car with id {%s} not found", carId));
        }
        if (rentServiceFeignClient.existsActiveRent(null, carId, null)) {
            throw new BadArgumentException(String.format("Cannot delete car with id {%s}, because there are active rents with this car", carId));
        }

        Optional<CarEntity> car = carRepository.findById(carId);
        carRepository.delete(car.get());

        return carMapper.carEntityToCarDto(car.get());
    }
}
