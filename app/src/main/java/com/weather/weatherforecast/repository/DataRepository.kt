package com.weather.weatherforecast.repository

import com.weather.weatherforecast.WeatherApi
import com.weather.weatherforecast.entities.RequestDataItem
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import com.weather.weatherforecast.entities.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val weatherApi: WeatherApi,
) {

    suspend fun getWeatherData() = withContext(Dispatchers.IO) {
        try {
            val result = weatherApi.getWeatherData("臺北市", "MinT")
            return@withContext Result.Success(result)
        } catch (exception: Exception) {
            return@withContext Result.Error(exception)
        }
    }
}