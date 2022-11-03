package com.test.devs

import com.google.common.truth.Truth.assertThat
import com.weather.weatherforecast.WeatherApi
import com.weather.weatherforecast.entities.Result
import com.weather.weatherforecast.repository.DataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class ApiTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockWebServer: MockWebServer

    private lateinit var weatherApi: WeatherApi

    private lateinit var dataRepository: DataRepository


    @Before
    fun setUp() {
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
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun check_if_400_is_thrown() {
        val response = MockResponse().setBody("The client messed this up").setResponseCode(400)
        mockWebServer.enqueue(response)
        runBlocking {
            val result = dataRepository.getWeatherData()
            assertThat((result as Result.Error).exception is HttpException).isTrue()
            assertThat(result.exception.message == "The client messed this up")
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun check_successful_response() {
        val fakeResponse =  FakeResponseData.buildData()
        val response = MockResponse().setBody(fakeResponse.first).setResponseCode(200)
        mockWebServer.enqueue(response)
        runBlocking {
            val result = dataRepository.getWeatherData()
            assertThat(result is Result.Success).isTrue()
            assertThat((result as Result.Success).data == fakeResponse.second)
        }
    }
}