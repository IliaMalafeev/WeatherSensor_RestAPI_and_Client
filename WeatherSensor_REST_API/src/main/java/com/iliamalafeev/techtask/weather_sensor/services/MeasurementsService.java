package com.iliamalafeev.techtask.weather_sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iliamalafeev.techtask.weather_sensor.models.Measurement;
import com.iliamalafeev.techtask.weather_sensor.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public long countRainyDays(boolean raining) {
        return measurementsRepository.findAllByRainingIsTrue(raining);
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        /*
        In order to covert String line with sensor from our JSON request body into Sensor model
        we have to manually find this Sensor object in DB and put it into persistent context,
        then hibernate will be able to relate our measurement with its sensor
         */
        measurement.setSensor(sensorsService.findSensorByName(measurement.getSensor().getName()).get());
        measurement.setMeasuredAt(LocalDateTime.now());
    }
}
