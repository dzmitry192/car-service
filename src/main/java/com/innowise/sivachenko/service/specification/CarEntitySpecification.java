package com.innowise.sivachenko.service.specification;

import com.innowise.sivachenko.model.entity.CarEntity;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CarEntitySpecification {

    public static Specification<CarEntity> withName(String name) {
        return (car, query, builder) -> name != null ? builder.equal(car.get("name"), name) : null;
    }

    public static Specification<CarEntity> withYearOfCar(Integer yearOfCar) {
        return (car, query, builder) -> yearOfCar != null ? builder.equal(car.get("yearOfCar"), yearOfCar) : null;
    }

    public static Specification<CarEntity> withHorsepower(Integer horsepower) {
        return (car, query, builder) -> horsepower != null ? builder.equal(car.get("horsepower"), horsepower) : null;
    }

    public static Specification<CarEntity> withEngineCapacity(Float engineCapacity) {
        return (car, query, builder) -> engineCapacity != null ? builder.equal(car.get("engineCapacity"), engineCapacity) : null;
    }

    public static Specification<CarEntity> withSeats(Short seats) {
        return (car, query, builder) -> seats != null ? builder.equal(car.get("seats"), seats) : null;
    }

    public static Specification<CarEntity> withColor(String color) {
        return (car, query, builder) -> color != null ? builder.equal(car.get("color"), color) : null;
    }

    public static Specification<CarEntity> withCarMake(String carMake) {
        return (car, query, builder) -> carMake != null ? builder.equal(car.get("carMake"), carMake) : null;
    }

    public static Specification<CarEntity> withEngineType(EngineType engineType) {
        return (car, query, builder) -> engineType != null ? builder.equal(car.get("engineType"), engineType) : null;
    }

    public static Specification<CarEntity> withBodyType(CarBodyType bodyType) {
        return (car, query, builder) -> bodyType != null ? builder.equal(car.get("bodyType"), bodyType) : null;
    }

    public static Specification<CarEntity> withTransmissionType(TransmissionType transmissionType) {
        return (car, query, builder) -> transmissionType != null ? builder.equal(car.get("transmissionType"), transmissionType) : null;
    }
}
