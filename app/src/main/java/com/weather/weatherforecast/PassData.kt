package com.weather.weatherforecast

import android.os.Parcelable
import com.weather.weatherforecast.entities.RequestDataItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassData(
    val startTime: String,
    val endTime: String,
    val temperature: String
) : Parcelable
