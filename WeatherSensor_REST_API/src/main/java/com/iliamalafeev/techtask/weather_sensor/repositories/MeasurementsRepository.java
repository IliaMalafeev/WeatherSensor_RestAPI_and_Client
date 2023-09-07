package com.iliamalafeev.techtask.weather_sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.iliamalafeev.techtask.weather_sensor.models.Measurement;


@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    @Query("select count(*) from Measurement where raining = ?1")
    long findAllByRainingIsTrue(boolean raining);
}
