package com.test.devs.viewmodel

import android.provider.ContactsContract
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.weather.weatherforecast.DataListViewModel
import com.weather.weatherforecast.WeatherApi
import com.weather.weatherforecast.datastore.PreferencesDataStore
import com.weather.weatherforecast.repository.DataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ViewModelTest {

    private lateinit var viewModel: DataListViewModel

    private lateinit var dataRepository: DataRepository

    private lateinit var mockWebServer: MockWebServer

    private lateinit var weatherApi: WeatherApi

    private lateinit var preferencesDataStore: PreferencesDataStore

    @Before
    fun setUp() {
        preferencesDataStore = PreferencesDataStore(
            InstrumentationRegistry.getInstrumentation().targetContext
        )
        mockWebServer = MockWebServer()
        weatherApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(
                OkHttpClient.Builder()
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
        dataRepository = DataRepository(weatherApi)
        viewModel = DataListViewModel(dataRepository, preferencesDataStore)
    }
    @Test
    fun test__weatherData_() {
        viewModel.getWeatherData()
        runBlocking {
            val result = viewModel.weatherData.collect()
            assertThat(result).isNotNull()
        }


    }
}