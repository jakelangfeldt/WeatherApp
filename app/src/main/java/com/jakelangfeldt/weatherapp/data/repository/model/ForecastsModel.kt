package com.jakelangfeldt.weatherapp.data.repository.model

import com.google.gson.annotations.SerializedName

data class ForecastsModel(

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("forcasts")
    val forecasts: List<Forecast>? = null,
)

data class Forecast(
    @SerializedName("temperature")
    val temperature: Temperature? = null,
)

data class Temperature(
    @SerializedName("min")
    val min: Double? = null,

    @SerializedName("max")
    val max: Double? = null,
)
