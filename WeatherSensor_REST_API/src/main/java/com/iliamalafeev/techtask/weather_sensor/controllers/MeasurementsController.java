package com.iliamalafeev.techtask.weather_sensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.iliamalafeev.techtask.weather_sensor.dto.MeasurementDTO;
import com.iliamalafeev.techtask.weather_sensor.dto.MeasurementsResponse;
import com.iliamalafeev.techtask.weather_sensor.models.Measurement;
import com.iliamalafeev.techtask.weather_sensor.services.MeasurementsService;
import com.iliamalafeev.techtask.weather_sensor.util.MeasurementErrorResponse;
import com.iliamalafeev.techtask.weather_sensor.util.MeasurementException;
import com.iliamalafeev.techtask.weather_sensor.util.MeasurementValidator;

import java.util.stream.Collectors;

import static com.iliamalafeev.techtask.weather_sensor.util.ErrorsUtil.returnMeasurementError;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService,
                                  MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public MeasurementsResponse showAllMeasurements() {
        return new MeasurementsResponse(measurementsService.findAll()
                .stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public String countRainyDays() {
        return "Total rainy days: " + measurementsService.countRainyDays(true);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            returnMeasurementError(bindingResult);
        }
        measurementsService.addMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}