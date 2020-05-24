package com.minakshi.weatherlogger.networking;


import androidx.lifecycle.MutableLiveData;

import com.minakshi.weatherlogger.model.WeatherDataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataRepository {

    private static WeatherDataRepository weatherDataRepository;

    public static WeatherDataRepository getInstance(){
        if (weatherDataRepository == null){
            weatherDataRepository = new WeatherDataRepository();
        }
        return weatherDataRepository;
    }

    private CurrentWeatherDataApi weatherDataApi;

    public WeatherDataRepository(){
        weatherDataApi = RetrofitService.createService(CurrentWeatherDataApi.class);
    }

    public MutableLiveData<WeatherDataResponse> getWeatherDetails(String latitude, String longitude){
        MutableLiveData<WeatherDataResponse> weatherData = new MutableLiveData<>();
        weatherDataApi.getWeatherData(latitude, longitude, "0e6418d8c02a59a5e2212b432ae483f4").enqueue(new Callback<WeatherDataResponse>() {
            @Override
            public void onResponse(Call<WeatherDataResponse> call,
                                   Response<WeatherDataResponse> response) {
                if (response.isSuccessful()){
                    weatherData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherDataResponse> call, Throwable t) {
                weatherData.setValue(null);
            }
        });
        return weatherData;
    }
}
