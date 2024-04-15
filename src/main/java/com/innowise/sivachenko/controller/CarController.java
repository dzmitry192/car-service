package com.innowise.sivachenko.controller;

import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.enums.CarBodyType;
import com.innowise.sivachenko.model.enums.EngineType;
import com.innowise.sivachenko.model.enums.TransmissionType;
import com.innowise.sivachenko.model.exception.BadArgumentException;
import com.innowise.sivachenko.service.CarServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/car-service")
@RequiredArgsConstructor
public class CarController {

    private final CarServiceImpl carService;

    @Operation(
            summary = "Get page of cars with query params"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = {@Content(mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "name", description = "Name of the car"),
            @Parameter(name = "yearOfCar", description = "Year of the car"),
            @Parameter(name = "horsepower", description = "Number of horsepower"),
            @Parameter(name = "engineCapacity", description = "Engine capacity"),
            @Parameter(name = "seats", description = "Number of seats"),
            @Parameter(name = "color", description = "Color of the car"),
            @Parameter(name = "carMake", description = "The car make"),
            @Parameter(name = "engineType", description = "The type of engine"),
            @Parameter(name = "bodyType", description = "The type of car's body"),
            @Parameter(name = "transmissionType", description = "The type of car's transmission"),
            @Parameter(name = "page", description = "Number of the page for pagination"),
            @Parameter(name = "size", description = "Number of cars per page")
    })
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Page<CarDto>> getCars(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "yearOfCar", required = false) Integer yearOfCar,
            @RequestParam(name = "horsepower", required = false)  Integer horsepower,
            @RequestParam(name = "engineCapacity", required = false) Float engineCapacity,
            @RequestParam(name = "seats", required = false) Short seats,
            @RequestParam(name = "color", required = false)  String color,
            @RequestParam(name = "carMake", required = false)  String carMake,
            @RequestParam(name = "engineType", required = false) EngineType engineType,
            @RequestParam(name = "bodyType", required = false) CarBodyType bodyType,
            @RequestParam(name = "transmissionType", required = false) TransmissionType transmissionType,
            @RequestParam(name = "page", required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) @Max(100) Integer size
    ) throws BadArgumentException {
        return ResponseEntity.ok(carService.getCars(name, yearOfCar, horsepower, engineCapacity, seats, color, carMake, engineType, bodyType, transmissionType, PageRequest.of(page, size)));
    }

    @Operation(
            summary = "Get car by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = {@Content(mediaType = "application/json")})
    })
    @Parameter(name = "carId", description = "Car id that you want to get")
    @GetMapping("/{carId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CarDto> getCarById(@PathVariable(name = "carId") Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @Operation(
            summary = "Create car"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CarDto> createCar(@Valid CreateCarDto createCarDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(createCarDto));
    }

    @Operation(
            summary = "Update car info"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = {@Content(mediaType = "application/json")})
    })
    @Parameter(name = "carId", description = "Car id that you want to update")
    @PutMapping("/{carId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CarDto> updateCarById(@PathVariable(name = "carId") Long carId,
                                                @Valid UpdateCarDto updateCarDto) {
        return ResponseEntity.ok(carService.updateCar(carId, updateCarDto));
    }

    @Operation(
            summary = "Update car info"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = {@Content(mediaType = "application/json")})
    })
    @Parameter(name = "carId", description = "Car id that you want to delete")
    @DeleteMapping("/{carId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CarDto> deleteCarById(@PathVariable(name = "carId") Long carId) {
        return ResponseEntity.ok(carService.deleteCar(carId));
    }
}
