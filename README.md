# Weather Sensor REST API and Client Project

Project is done as a technical task example.

Stack: Java, Spring Boot, Spring Data JPA, Spring REST, Hibernate, Spring Validator, PostgreSQL, RestTemplate, xchart.

## Technical Task

### Brief description of the task

Imagine that you have a **meteorological (weather) sensor**. This sensor measures ambient temperature, 
and it can determine whether it rains or not. We want to receive data from such sensor and store it
in DataBase, so that we can process it later.

Sensor has internet connection, therefore it can send HTTP requests with JSON data to our server.

Since we do not possess a real sensor, we will use our computer to simulate one. It means that we will 
run local server with **REST API application**, and we will send HTTP requests to that application also 
from our computer to simulate the sensor itself.

Overall we need to create 2 applications:

* REST API server-side application;
* Client java-application to send data to server.

### 1 Part - REST API required functionality

Given are HTTP methods, API addresses, request data 
and functionality that must be provided as a result of the request.

#### POST .../sensors/registration

```json
{ 
  "name": "Sensor name"
}
```

Registers new sensor in the DataBase. Sensors only have one field - name. 
Name should be 3 to 30 symbols included, and validated to be unique.

#### POST .../measurements/add

```json
{ 
  "value": 24.7,
  "raining": false,
  "sensor": {
    "name": "Sensor name"
  }
}
```

Adds new measurement to the measurements table in DataBase. This simulates a request from real sensor:

* **Value** field contains temperature, it has to be from -100 to 100, not empty;
* **Raining** field contains boolean value, true if it's raining, not empty;
* **Sensor** field contains sensor itself as an object, not empty and there must be a sensor 
registered with this name in a sensors table. 

Each row in this table must contain all three values that come from a request.
All values must be validated. Additionally, server side must assign **measurement timestamp** to each 
measurement and save it to DataBase as well.

#### GET .../measurements

Returns all the measurements from DataBase.

#### GET .../measurements/rainyDaysCount

Returns the amount of rainy days from DataBase.

#### In total, 4 addresses in REST API application:

* Sensor registration
* Measurement adding
* Retrieving all measurements
* Retrieving rainy days count

### 2 Part - Client application required functionality

Create a client application and use it as follows:

* **Register a new sensor** through a client;
* **Send 1000 measurement requests** with random temperatures and "raining" values from recently registered sensor.
* Use your client to **receive all the measurements** from the DataBase
* Use your client to **receive rainy days count** from the DataBase
* **Build a temperature chart** using 1000 measurements acquired in a previous steps
* **Build a timestamp - temperature chart** using 1000 measurements acquired in a previous steps

## Implementation

Source code in this repository contains all required configurations and functionality. 
It is a fully complete functional project. I enhanced a client project a little, 
so that it simulates a choice of buttons to be pressed by user, e.g.: 

* If you want to see all measurements - press 4;
* If you want to see rainy days count - press 5;
* If you want to see temperature chart - press 6;

If you want to run and test it - you have to create a test database on your local machine
and fill out missing fields in `application.properties` file
(do not forget to remove ".blanc" from file name) inside REST API project folder. 
I have also added sql script file "postgres_table_creation_commands.sql" there with commands 
to create the necessary tables.