package com.iliamalafeev.techtask.weather_sensor.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnSensorError(BindingResult bindingResult) {
        throw new SensorException(buildErrorMessage(bindingResult));
    }

    public static void returnMeasurementError(BindingResult bindingResult) {
        throw new MeasurementException(buildErrorMessage(bindingResult));
    }

    private static String buildErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}
