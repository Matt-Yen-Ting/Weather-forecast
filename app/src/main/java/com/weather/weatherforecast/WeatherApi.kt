package com.weather.weatherforecast

import com.slack.eithernet.ApiResult
import com.weather.weatherforecast.entities.RequestDataItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @Headers("Authorization: CWB-CD7CAD6E-D841-4157-A03F-AE67C9AA61A7")
    @GET("v1/rest/datastore/F-C0032-001")
    suspend fun getWeatherData(
        @Query("locationName") locationName: String,
        @Query("elementName") elementName: String
    ): RequestDataItem
}