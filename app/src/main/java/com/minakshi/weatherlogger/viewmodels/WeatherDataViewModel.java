package com.minakshi.weatherlogger.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.minakshi.weatherlogger.model.WeatherDataResponse;
import com.minakshi.weatherlogger.networking.WeatherDataRepository;


public class WeatherDataViewModel extends ViewModel {

    private MutableLiveData<WeatherDataResponse> mutableLiveData;
    private WeatherDataRepository weatherDataRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        weatherDataRepository = WeatherDataRepository.getInstance();
    }

    public void getWeatherData(String latitude, String longitude) {
        mutableLiveData = weatherDataRepository.getWeatherDetails(latitude, longitude);
    }

    public LiveData<WeatherDataResponse> getWeatherDataRepository() {
        return mutableLiveData;
    }

}
