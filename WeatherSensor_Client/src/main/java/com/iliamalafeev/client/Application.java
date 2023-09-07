package com.iliamalafeev.client;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Client client = new Client("TestSensor");
        System.out.println("""
                Please, choose an option:
                ---> If you want to register a new Sensor - press 1;
                ---> If you want to add 1 measurement - press 2;
                ---> If you want to add 1000 measurements - press 3;
                ---> If you want to see all measurements - press 4;
                ---> If you want to see rainy days count - press 5;
                ---> If you want to see temperature chart - press 6;
                ---> If you want to see time - temperature chart - press 7""");

        Scanner scanner = new Scanner(System.in);
        int button = scanner.nextInt();
        scanner.close();

        switch (button) {
            case 1 -> {
                String response = client.registerSensor();
                System.out.println(response);
            }
            case 2 -> {
                String response = client.addMeasurement();
                System.out.println(response);
            }
            case 3 -> {
                String response = client.addThousandMeasurements();
                System.out.println(response);
            }
            case 4 -> {
                List<MeasurementDTO> measurements = client.getAllMeasurements();
                for (MeasurementDTO measurement : measurements) System.out.println(measurement);
            }
            case 5 -> {
                String response = client.getRainyDaysCount();
                System.out.println(response);
            }
            case 6 -> client.buildTemperatureChart();
            case 7 -> client.buildTimeTemperatureChart();
        }
    }
}