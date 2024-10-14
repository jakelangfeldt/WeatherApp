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

    @SerializedName("sunrise")
    val sunrise: Long? = null,

    @SerializedName("sunset")
    val sunset: Long? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("temp")
    val temp: Temperature? = null,

    @SerializedName("wind_speed")
    val windSpeed: Double? = null,

    @SerializedName("wind_deg")
    val windDegrees: Int? = null,

    @SerializedName("weather")
    val weatherList: List<Weather>? = null,

    @SerializedName("uvi")
    val uvi: Double? = null,
)

data class Temperature(
    @SerializedName("min")
    val min: Double? = null,

    @SerializedName("max")
    val max: Double? = null,
)

data class Weather(
    @SerializedName("icon")
    val icon: String? = null,
)
