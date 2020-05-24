package com.minakshi.weatherlogger.model;

public class WeatherReading {

    private int id;
    private String location;
    private Double temperature;
    private Long timeStamp;
    private String description;
    private int humidity;

    public WeatherReading(int id, String location, Double temperature, Long timeStamp, String description, int humidity) {
        this.id = id;
        this.location = location;
        this.temperature = temperature;
        this.timeStamp = timeStamp;
        this.description = description;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity() {
        return humidity;
    }
}
