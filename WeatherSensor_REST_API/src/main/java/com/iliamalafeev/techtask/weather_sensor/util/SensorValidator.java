package com.iliamalafeev.techtask.weather_sensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.iliamalafeev.techtask.weather_sensor.models.Sensor;
import com.iliamalafeev.techtask.weather_sensor.services.SensorsService;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if (sensorsService.findSensorByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name is already registered");
        }
    }
}
