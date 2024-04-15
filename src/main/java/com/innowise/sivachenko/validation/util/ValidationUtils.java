package com.innowise.sivachenko.validation.util;

import com.innowise.sivachenko.model.exception.BadArgumentException;

import java.time.LocalDate;

public class ValidationUtils {

    public static void isValidParams(String name, Integer year, Integer horsepower, Float engineCapacity, Short seats, String color, String carMake) throws BadArgumentException {
        isValidName(name);
        isValidYear(year);
        isValidHorsepower(horsepower);
        isValidEngineCapacity(engineCapacity);
        isValidSeats(seats);
        isValidColor(color);
        isValidCarMake(carMake);
    }

    public static void isValidName(String name) throws BadArgumentException {
        if (name != null) {
            if (!name.matches("^$|[a-zA-Zа-яА-Я0-9]*$")) {
                throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", name, "name"));
            }
        }
    }

    public static void isValidYear(Integer year) throws BadArgumentException {
        if (!(year == null || (year >= 1885 && year <= LocalDate.now().getYear()))) {
            throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", year, "year"));
        }
    }

    public static void isValidHorsepower(Integer horsepower) throws BadArgumentException {
        if (!(horsepower == null || horsepower > 0)) {
            throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", horsepower, "horsepower"));
        }
    }

    public static void isValidEngineCapacity(Float engineCapacity) throws BadArgumentException {
        if (!(engineCapacity == null || engineCapacity > 0)) {
            throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", engineCapacity, "engineCapacity"));
        }
    }

    public static void isValidSeats(Short seats) throws BadArgumentException {
        if (!(seats == null || seats > 0)) {
            throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", seats, "seats"));
        }
    }

    public static void isValidColor(String color) throws BadArgumentException {
        if (color != null) {
            if (!(color.matches("^$|[a-zA-Zа-яА-Я]*$"))) {
                throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", color, "color"));
            }
        }
    }

    public static void isValidCarMake(String carMake) throws BadArgumentException {
        if (carMake != null) {
            if (!(carMake.matches("^$|[a-zA-Zа-яА-Я0-9]*"))) {
                throw new BadArgumentException(String.format("Invalid value {%s} in field {%s}", carMake, "carMake"));
            }
        }
    }

}
