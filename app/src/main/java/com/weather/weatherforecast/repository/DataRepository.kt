package com.weather.weatherforecast.repository

import com.weather.weatherforecast.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val weatherApi: WeatherApi,
) {

    suspend fun getWeatherData() = withContext(Dispatchers.IO) {
        weatherApi.getWeatherData("臺北市", "MinT")
    }


}