package com.iliamalafeev.techtask.weather_sensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotNull(message = "Cannot be left empty")
    @NotEmpty(message = "Cannot be left empty")
    @Size(min = 3, max = 30, message = "Sensor name must contain 3-30 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
