package com.minakshi.weatherlogger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataResponse {

    @SerializedName("main")
    @Expose
    private WeatherDetail weatherDetail;

    @SerializedName("weather")
    @Expose
    private List<WeatherData> weatherDataList = null;

    @SerializedName("name")
    @Expose
    private String locationName;

    public WeatherDetail getWeatherDetail() {
        return weatherDetail;
    }

    public void setWeatherDetail(WeatherDetail weatherDetail) {
        this.weatherDetail = weatherDetail;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
