package com.jakelangfeldt.weatherapp.ui.viewmodel.state

data class ForecastsState(
    val location: String? = null,
    val forecasts: List<Forecast> = emptyList(),
    val selectedForecast: Forecast? = null,
)

data class Forecast(
    val location: String? = null,
    val date: String? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val summary: String? = null,
    val wind: String? = null,
    val temperature: Temperature? = null,
    val weather: Weather? = null,
    val uvi: String? = null,
)

data class Temperature(
    val min: String? = null,
    val max: String? = null,
)

data class Weather(
    val iconUrl: String? = null,
)
