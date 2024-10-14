package com.jakelangfeldt.weatherapp.data.repository.model

import com.google.gson.annotations.SerializedName

data class ForecastsModel(

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("timezone")
    val timezone: String? = null,

    @SerializedName("forecasts")
    val forecasts: List<Forecast>? = null,
)

data class Forecast(
    @SerializedName("time")
    val time: Long? = null,

    @SerializedName("sunrise")
    val sunrise: Long? = null,

    @SerializedName("sunset")
    val sunset: Long? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("temperature")
    val temperature: Temperature? = null,

    @SerializedName("wind_speed")
    val windSpeed: Double? = null,

    @SerializedName("wind_degrees")
    val windDegrees: Int? = null,

    @SerializedName("weather_list")
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
