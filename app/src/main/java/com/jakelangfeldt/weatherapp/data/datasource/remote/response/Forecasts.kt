package com.jakelangfeldt.weatherapp.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class Forecasts(
    @SerializedName("timezone")
    val timezone: String? = null,

    @SerializedName("daily")
    val daily: List<Forecast>? = null,
)

data class Forecast(
    @SerializedName("dt")
    val dt: Long? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("temp")
    val temp: Temperature? = null,
)

data class Temperature(
    @SerializedName("min")
    val min: Double? = null,

    @SerializedName("max")
    val max: Double? = null,
)
