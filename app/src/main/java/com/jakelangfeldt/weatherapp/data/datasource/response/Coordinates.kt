package com.jakelangfeldt.weatherapp.data.datasource.response

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("lon")
    val lon: Double? = null,
)
