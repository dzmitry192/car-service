package com.innowise.sivachenko.model.entity;

import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "year_of_car")
    private Integer yearOfCar;

    @NotNull
    @Column(name = "horsepower")
    private Integer horsepower;

    @NotNull
    @Column(name = "engine_capacity")
    private Float engineCapacity;

    @NotNull
    @Column(name = "seats")
    private Short seats;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotBlank
    @Column(name = "car_make")
    private String carMake;

    @Column(name = "used_by")
    private Long usedByClientId;

    @NotNull
    @Min(1)
    @Column(name = "price_day")
    private Long priceDay;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "engine_type")
    private EngineType engineType;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "body_type")
    private CarBodyType bodyType;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;
}