package com.innowise.sivachenko.aspects;

import com.innowise.sivachenko.kafka.avro.AuditActionRequest;
import com.innowise.sivachenko.kafka.avro.AuditActionType;
import com.innowise.sivachenko.kafka.producer.AuditActionProducer;
import com.innowise.sivachenko.model.dto.request.CreateCarDto;
import com.innowise.sivachenko.model.dto.request.UpdateCarDto;
import com.innowise.sivachenko.model.dto.response.CarDto;
import com.innowise.sivachenko.model.exception.BadArgumentException;
import com.innowise.sivachenko.model.exception.ServiceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;

import static com.innowise.sivachenko.kafka.avro.AuditActionType.*;
import static com.innowise.sivachenko.kafka.avro.AuditServiceType.CAR;
import static java.lang.String.format;


@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditActionProducer auditActionProducer;

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.getCars())",
            returning = "result",
            argNames = "result"
    )
    public void getCars_afterReturning(ResponseEntity<Page<CarDto>> result) {
        log.info("Cars received successfully at {}", Instant.now());
        sendAuditActionRequest(
                format("Cars received successfully at {%s}", Instant.now()),
                GET,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.getCars())",
            throwing = "ex",
            argNames = "ex"
    )
    public void getCars_afterThrowing(Exception ex) {
        log.info("An error occurred while getting cars at {}, reason: {}, message: {}", Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("An error occurred while getting cars at {%s}, reason: {%s}, message: {%s}", Instant.now(), ex.getCause(), ex.getMessage()),
                GET,
                getStatusCode(ex)
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.getCarById()) && args(carId)",
            returning = "result",
            argNames = "carId,result"
    )
    public void getCarById_afterReturning(Long carId, ResponseEntity<CarDto> result) {
        log.info("Car with id {} received successfully at {}", carId, Instant.now());
        sendAuditActionRequest(
                format("Car with id {%s} received successfully at {%s}", carId, Instant.now()),
                GET,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.getCarById()) && args(carId)",
            throwing = "ex",
            argNames = "carId,ex"
    )
    public void getCarById_afterThrowing(Long carId, Exception ex) {
        log.info("An error occurred while getting car with id {} at {}, reason: {}, message: {}", carId, Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("An error occurred while getting car with id {%s} at {%s}, reason: {%s}, message: {%s}", carId, Instant.now(), ex.getCause(), ex.getMessage()),
                GET,
                getStatusCode(ex)
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.createCar()) && args(createCarDto)",
            returning = "result",
            argNames = "createCarDto,result"
    )
    public void createCar_afterReturning(CreateCarDto createCarDto, ResponseEntity<CarDto> result) {
        log.info("Car with body {} created successfully at {}", createCarDto, Instant.now());
        sendAuditActionRequest(
                format("Car with body {%s} created successfully at {%s}", createCarDto, Instant.now()),
                POST,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.createCar()) && args(createCarDto)",
            throwing = "ex",
            argNames = "createCarDto,ex"
    )
    public void createCar_afterThrowing(CreateCarDto createCarDto, Exception ex) {
        log.info("Error when creating a car with body {} at {}, reason: {}, message: {}", createCarDto, Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("Error when creating a car with body {%s} at {%s}, reason: {%s}, message: {%s}", createCarDto, Instant.now(), ex.getCause(), ex.getMessage()),
                POST,
                getStatusCode(ex)
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.updateCarById()) && args(carId,updateCarDto)",
            returning = "result",
            argNames = "carId,updateCarDto,result"
    )
    public void updateCarById_afterReturning(Long carId, UpdateCarDto updateCarDto, ResponseEntity<CarDto> result) {
        log.info("Car with id {} and new values {} updated successfully at {}", carId, updateCarDto, Instant.now());
        sendAuditActionRequest(
                format("Car with id {%s} and new values {%s} updated successfully at {%s}", carId, updateCarDto, Instant.now()),
                PUT,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.updateCarById()) && args(carId,updateCarDto)",
            throwing = "ex",
            argNames = "carId,updateCarDto,ex"
    )
    public void updateCarById_afterThrowing(Long carId, UpdateCarDto updateCarDto, Exception ex) {
        log.info("Error when updating a car with id {} and new values {} at {}, reason: {}, message: {}", carId, updateCarDto, Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("Error when updating a car with id {%s} and new values {%s} at {%s}, reason: {%s}, message: {%s}", carId, updateCarDto, Instant.now(), ex.getCause(), ex.getMessage()),
                PUT,
                getStatusCode(ex)
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.updateCarRenter()) && args(carId,clientId)",
            returning = "result",
            argNames = "carId,clientId,result"
    )
    public void updateCarRenter_afterReturning(Long carId, Long clientId, ResponseEntity<CarDto> result) {
        log.info("Car renter with id {} successfully changed for car with id {} at {}", clientId, carId, Instant.now());
        sendAuditActionRequest(
                format("Car renter with id {%s} successfully changed for car with id {%s} at {%s}", clientId, carId, Instant.now()),
                PATCH,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.updateCarRenter()) && args(carId,clientId)",
            throwing = "ex",
            argNames = "carId,clientId,ex"
    )
    public void updateCarRenter_afterThrowing(Long carId, Long clientId, Exception ex) {
        log.info("Error changing car renter, renter id {}, car id {}, at {}, reason: {}, message: {}", clientId, carId, Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("Error changing car renter, renter id {%s}, car id {%s}, at {%s}, reason: {%s}, message: {%s}", clientId, carId, Instant.now(), ex.getCause(), ex.getMessage()),
                PATCH,
                getStatusCode(ex)
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.deleteCarById()) && args(carId)",
            returning = "result",
            argNames = "carId,result"
    )
    public void deleteCarById_afterReturning(Long carId, ResponseEntity<CarDto> result) {
        log.info("Car with id {} deleted successfully at {}", carId, Instant.now());
        sendAuditActionRequest(
                format("Car with id {%s} deleted successfully at {%s}", carId, Instant.now()),
                DELETE,
                result.getStatusCode().value()
        );
    }

    @AfterThrowing(
            pointcut = "execution(* com.innowise.sivachenko.controller.CarController.deleteCarById()) && args(carId)",
            throwing = "ex",
            argNames = "carId,ex"
    )
    public void deleteCarById_afterThrowing(Long carId, Exception ex) {
        log.info("Error deleting car with id {} at {}, reason: {}, message: {}", carId, Instant.now(), ex.getCause(), ex.getMessage());
        sendAuditActionRequest(
                format("Error deleting car with id {%s} at {%s}, reason: {%s}, message: {%s}", carId, Instant.now(), ex.getCause(), ex.getMessage()),
                DELETE,
                getStatusCode(ex)
        );
    }

    private void sendAuditActionRequest(String info, AuditActionType actionType, int statusCode) {
        auditActionProducer.sendMessage(new AuditActionRequest(
                getUserEmail(),
                info,
                actionType,
                CAR,
                statusCode,
                Instant.now()
        ));
    }

    private int getStatusCode(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException || ex instanceof BadArgumentException) {
            return 400;
        } else if (ex instanceof AccessDeniedException) {
            return 403;
        } else if (ex instanceof EntityNotFoundException) {
            return 404;
        } else if (ex instanceof ServiceNotFoundException) {
            return 503;
        } else {
            return 500;
        }
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getClaimAsString("email");
        }
        return null;
    }

}
