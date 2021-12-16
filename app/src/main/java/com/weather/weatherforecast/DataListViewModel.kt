package com.weather.weatherforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.weatherforecast.datastore.PreferencesDataStore
import com.weather.weatherforecast.entities.RequestDataItem
import com.weather.weatherforecast.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataListViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    val enableAppCount = preferencesDataStore.getEnableAppCount().take(1)

    private val _weatherData =
        MutableSharedFlow<List<RequestDataItem.Record.Location.WeatherElement.WeatherInfo>>()
    val weatherData = _weatherData.asSharedFlow()

    fun setOnceEnableAppCount(count: Int) {
        viewModelScope.launch {
            preferencesDataStore.setEnableAppCount(count)
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val result = dataRepository.getWeatherData()
            _weatherData.emit(result.records.location[0].weatherElement[0].weatherInfo)
        }
    }
}