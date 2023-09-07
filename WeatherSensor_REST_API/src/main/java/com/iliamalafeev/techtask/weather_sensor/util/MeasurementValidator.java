package com.iliamalafeev.techtask.weather_sensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.iliamalafeev.techtask.weather_sensor.models.Measurement;
import com.iliamalafeev.techtask.weather_sensor.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor().getName() == null) {
            errors.rejectValue("sensor", "Sensor name cannot be null");
            return;
        }
        if (sensorsService.findSensorByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "Sensor name is incorrect - no such sensor registered");
        }
    }
}
