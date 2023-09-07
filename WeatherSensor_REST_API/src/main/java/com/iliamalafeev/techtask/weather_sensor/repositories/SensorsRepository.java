package com.iliamalafeev.techtask.weather_sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.iliamalafeev.techtask.weather_sensor.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByName(String name);
}
