package com.iliamalafeev.techtask.weather_sensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MeasurementDTO {

    @NotNull(message = "Please, enter some value")
    @Min(value = -100, message = "Temperature value must be more than -100")
    @Max(value = 100, message = "Temperature value must be less than 100")
    private Double value;

    @NotNull(message = "Please, enter true or false")
    private Boolean raining;

    @NotNull(message = "Please, enter sensor name")
    private SensorDTO sensor;

    private LocalDateTime measuredAt;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }
}
