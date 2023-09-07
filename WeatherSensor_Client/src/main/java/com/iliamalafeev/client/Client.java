package com.iliamalafeev.client;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Client {

    private static final RestTemplate TEMPLATE = new RestTemplate();
    private static final String URL = "http://localhost:8080";
    private final String sensorName;

    public Client(String sensorName) {
        this.sensorName = sensorName;
    }

    public static String makePostRequestWithJSON(String url, Map<String, Object> json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(json, headers);
        String response;
        try {
            response = TEMPLATE.postForObject(url,request, String.class);
        } catch (HttpClientErrorException e) {
            response = "Oops! Something went wrong: " + e.getMessage();
        }
        return response;
    }

    // Registration of a new sensor
    public String registerSensor() {
        Map<String, Object> jsonToSend = new HashMap<>();
        jsonToSend.put("name", sensorName);
        return makePostRequestWithJSON(URL + "/sensors/registration", jsonToSend);
    }

    // Adding a random measurement for a registered sensor
    public String addMeasurement() {
        Map<String, Object> jsonToSend = new HashMap<>();
        Random random = new Random();
        double temp = random.nextDouble(-50, 50);
        boolean raining = random.nextBoolean();
        jsonToSend.put("value", temp);
        jsonToSend.put("raining", raining);
        jsonToSend.put("sensor", Map.of("name", sensorName));
        return makePostRequestWithJSON(URL + "/measurements/add", jsonToSend);
    }

    // Adding 1000 random measurements for a registered sensor
    public String addThousandMeasurements() {
        String response = "";
        for (int i = 0; i < 1000; i++) response = addMeasurement();
        return response;
    }

    // Getting all the measurements from the server
    public List<MeasurementDTO> getAllMeasurements() {
        MeasurementsResponse response = TEMPLATE.getForObject(URL + "/measurements", MeasurementsResponse.class);
        if (response == null || response.getMeasurements() == null) return Collections.emptyList();
        else return response.getMeasurements();
    }

    // Getting rainy days count from the server
    public String getRainyDaysCount() {
        return TEMPLATE.getForObject(URL + "/measurements/rainyDaysCount", String.class);
    }

    /*
    Getting all the measurements from the server
    Then extracting temperature and a timestamp of each measurement and collecting into Lists
    Then building and displaying a Time - Temperature chart based on the measurements
     */
    public void buildTimeTemperatureChart() {
        List<MeasurementDTO> measurements = getAllMeasurements();

        List<Double> temps = measurements.stream().map(MeasurementDTO::getValue).collect(Collectors.toList());
        List<Long> times = measurements.stream().map(MeasurementDTO::getMeasuredAt)
                .map(x -> {
                    long seconds = x.getLong(ChronoField.SECOND_OF_DAY);
                    long micros = x.getLong(ChronoField.MICRO_OF_SECOND);
                    return seconds * 1_000_000 + micros;
                }).toList();

        XYChart chart = QuickChart.getChart("Temperature Chart", "Timestamp", "Temperature", "Time/Temp", times, temps);
        new SwingWrapper(chart).displayChart();
    }

    public void buildTemperatureChart() {
        List<MeasurementDTO> measurements = getAllMeasurements();

        double[] temps = measurements.stream().map(MeasurementDTO::getValue).mapToDouble(x -> x).toArray();
        double[] dots = IntStream.range(0, temps.length).asDoubleStream().toArray();

        XYChart chart = QuickChart.getChart("Temperature Chart", "Timestamp", "Temperature", "Time/Temp", dots, temps);
        new SwingWrapper(chart).displayChart();
    }
}