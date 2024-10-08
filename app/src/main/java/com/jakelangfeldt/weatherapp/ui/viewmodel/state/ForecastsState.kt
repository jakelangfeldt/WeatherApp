package com.jakelangfeldt.weatherapp.ui.viewmodel.state

data class ForecastsState(
    val location: String? = null,
    val forecasts: List<Forecast> = emptyList(),
)

data class Forecast(
    val temperature: Temperature? = null,
)

data class Temperature(
    val min: String? = null,
    val max: String? = null,
)
