package com.test.devs

import com.squareup.moshi.Moshi
import com.weather.weatherforecast.entities.RequestDataItem
import com.squareup.moshi.adapter


@ExperimentalStdlibApi
object FakeResponseData {

    fun buildData(): Pair<String, RequestDataItem> {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<RequestDataItem>()
        val weatherElementList = mutableListOf<RequestDataItem.Record.Location.WeatherElement>()
        val locationList = mutableListOf<RequestDataItem.Record.Location>()
        weatherElementList.add(
            RequestDataItem.Record.Location.WeatherElement(
                "testElement",
                listOf(RequestDataItem.Record.Location.WeatherElement.WeatherInfo(
                    "2022-01-01",
                    "2022-02-01",
                    RequestDataItem.Record.Location.WeatherElement.WeatherInfo.Parameter(
                        "Test_Parameter_Name",
                        "Test_Parameter_Unit"
                    ))
                )
            )
        )
        locationList.add(RequestDataItem.Record.Location(weatherElementList.toList()))
        val record = RequestDataItem.Record(locationList.toList())
        val requestDataItem = RequestDataItem(record)
       return adapter.toJson(requestDataItem) to requestDataItem
    }
}