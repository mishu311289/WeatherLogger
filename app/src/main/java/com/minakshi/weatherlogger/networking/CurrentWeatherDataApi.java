package com.minakshi.weatherlogger.networking;

import com.minakshi.weatherlogger.model.WeatherDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherDataApi {
    @GET("weather")
    Call<WeatherDataResponse> getWeatherData(@Query("lat") String latitude,
                                             @Query("lon") String longitude,
                                             @Query("appid") String appId);
}
