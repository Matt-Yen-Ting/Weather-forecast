package com.weather.weatherforecast

import androidx.lifecycle.ViewModel
import com.weather.weatherforecast.datastore.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowWeatherInfoViewModel @Inject constructor(
    preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    val enableAppCount = preferencesDataStore.getEnableAppCount()

}