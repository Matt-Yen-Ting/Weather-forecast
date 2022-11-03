package com.weather.weatherforecast.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestDataItem(
    @Json(name = "records") val records: Record
) {
    @JsonClass(generateAdapter = true)
    data class Record(
        @Json(name = "location") val location: List<Location>
    ) {
        @JsonClass(generateAdapter = true)
        data class Location(
            @Json(name = "weatherElement") val weatherElement: List<WeatherElement>
        ) {
            @JsonClass(generateAdapter = true)
            data class WeatherElement(
                @Json(name = "elementName") val elementName: String,
                @Json(name = "time") val weatherInfo: List<WeatherInfo>
            ) {
                @JsonClass(generateAdapter = true)
                data class WeatherInfo(
                    @Json(name = "startTime") val startTime: String,
                    @Json(name = "endTime") val endTime: String,
                    @Json(name = "parameter") val parameter: Parameter
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Parameter(
                        @Json(name = "parameterName") val parameterName: String,
                        @Json(name = "parameterUnit") val parameterUnit: String
                    )
                }
            }
        }
    }
}
