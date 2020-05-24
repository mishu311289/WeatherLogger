package com.minakshi.weatherlogger

import android.content.Context
import android.content.SharedPreferences
import com.minakshi.weatherlogger.model.WeatherReading
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Utils {
    companion object {
        val WEATHER_PREFS_TAG = "weatherPrefs"
        val WEATHER_READING_DATA_KEY = "weatherReadingData"

        fun getDate(timestamp: Long): String {
            try {
                val sdf = SimpleDateFormat("MM-dd-yyyy hh:mm")
                val netDate = Date(timestamp)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

        fun saveWeatherReading(weatherReading: WeatherReading, context: Context) {
            val weatherReadingList = getWeatherReadingList(context)
            weatherReadingList.add(weatherReading)

            val gson = Gson()
            val jsonReading = gson.toJson(weatherReadingList)

            val sharedPref: SharedPreferences = context.getSharedPreferences(WEATHER_PREFS_TAG, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            editor.putString(WEATHER_READING_DATA_KEY, jsonReading)
            editor.commit()
        }

        fun getWeatherReadingList(context: Context): ArrayList<WeatherReading> {
            val gson = Gson()
            var weatherReadingList = arrayListOf<WeatherReading>()
            val sharedPref: SharedPreferences = context.getSharedPreferences(WEATHER_PREFS_TAG, Context.MODE_PRIVATE)
            val weatherReadingData = sharedPref.getString(WEATHER_READING_DATA_KEY, "")

            val type = object : TypeToken<List<WeatherReading>>() {}.type

            if (!weatherReadingData.isNullOrEmpty()) {
                weatherReadingList = gson.fromJson(weatherReadingData, type)
            }

            return weatherReadingList
        }
    }
}