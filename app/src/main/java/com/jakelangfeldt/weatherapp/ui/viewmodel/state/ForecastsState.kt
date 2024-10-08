package com.jakelangfeldt.weatherapp.ui.viewmodel.state

data class ForecastsState(
    val location: String? = null,
    val forecasts: List<Forecast> = emptyList(),
    val selectedForecast: Forecast? = null,
)

data class Forecast(
    val location: String? = null,
    val date: String? = null,
    val dayOfWeek: String? = null,
    val summary: String? = null,
    val temperature: Temperature? = null,
)

data class Temperature(
    val min: String? = null,
    val max: String? = null,
)
