package com.minakshi.weatherlogger

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.minakshi.weatherlogger.adapters.WeatherDataAdapter
import com.minakshi.weatherlogger.model.WeatherDataResponse
import com.minakshi.weatherlogger.model.WeatherReading
import com.minakshi.weatherlogger.viewmodels.WeatherDataViewModel
import java.util.*


class MainActivity : AppCompatActivity() {
    var weatherDataList = ArrayList<WeatherReading>()
    var weatherDataAdapter: WeatherDataAdapter? = null
    lateinit var rvHeadline: RecyclerView
    lateinit var weatherDataViewModel: WeatherDataViewModel
    val PERMISSION_ID = 42

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvHeadline = findViewById(R.id.rvNews)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        weatherDataList = Utils.getWeatherReadingList(this);

        weatherDataViewModel = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        weatherDataViewModel.init()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        if (weatherDataAdapter == null) {
            weatherDataAdapter = WeatherDataAdapter(this@MainActivity, weatherDataList)
            rvHeadline.layoutManager = LinearLayoutManager(this)
            rvHeadline.adapter = weatherDataAdapter
            rvHeadline.itemAnimator = DefaultItemAnimator()
            rvHeadline.isNestedScrollingEnabled = true
        } else {
            weatherDataAdapter!!.notifyDataSetChanged()
        }
    }

    private fun getWeatherDataForCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        fetchWeatherData(location.latitude.toString(), location.longitude.toString())
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            fetchWeatherData(mLastLocation.latitude.toString(), mLastLocation.longitude.toString())
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getWeatherDataForCurrentLocation()
            }
        }
    }

    private fun fetchWeatherData(latitude: String, longitude: String) {
        weatherDataViewModel.getWeatherData(latitude, longitude)

        weatherDataViewModel.weatherDataRepository.observe(this, Observer { weatherResponse: WeatherDataResponse ->
            val weatherData = weatherResponse.weatherDataList

            for (weatherReading in weatherData) {
                val weatherReading = WeatherReading(weatherReading.id, weatherResponse.locationName,
                        weatherResponse.weatherDetail.temp, System.currentTimeMillis(),
                        weatherReading.description, weatherResponse.weatherDetail.humidity)
                weatherDataList.add(weatherReading)
                Utils.saveWeatherReading(weatherReading, this)
            }

            weatherDataAdapter!!.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return if (id == R.id.action_save) {
            getWeatherDataForCurrentLocation()
            true
        } else super.onOptionsItemSelected(item)
    }
}