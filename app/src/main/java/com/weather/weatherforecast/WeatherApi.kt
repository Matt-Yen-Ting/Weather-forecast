package com.weather.weatherforecast

import android.graphics.Bitmap
import com.weather.weatherforecast.entities.RequestDataItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @Headers("Authorization: CWB-288CC3AA-ABFD-4115-87A8-B3865248286C")
    @GET("v1/rest/datastore/F-C0032-001")
    suspend fun getWeatherData(
        @Query("locationName") locationName: String,
        @Query("elementName") elementName: String
    ): RequestDataItem

    @GET("B01001040000_Login.IdentifyCode.go")
    suspend fun getVerificationCode(@Query("t") time: String): Bitmap
}