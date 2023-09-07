package com.iliamalafeev.client;

import java.time.LocalDateTime;

public class MeasurementDTO {
    private Double value;
    private Boolean raining;
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

    @Override
    public String toString() {
        return "Measurement {" +
                "temp: " + value +
                ", raining: " + raining +
                ", sensor: " + sensor.getName() +
                ", measuredAt: " + measuredAt +
                '}';
    }
}
