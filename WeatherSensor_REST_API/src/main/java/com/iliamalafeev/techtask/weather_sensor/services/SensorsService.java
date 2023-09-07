package com.iliamalafeev.techtask.weather_sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iliamalafeev.techtask.weather_sensor.models.Sensor;
import com.iliamalafeev.techtask.weather_sensor.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Optional<Sensor> findSensorByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void registerSensor(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
