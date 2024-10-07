package com.jakelangfeldt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakelangfeldt.weatherapp.BuildConfig
import com.jakelangfeldt.weatherapp.data.datasource.response.Coordinates
import com.jakelangfeldt.weatherapp.data.repository.OpenWeatherMapRepository
import com.jakelangfeldt.weatherapp.data.repository.Result
import com.jakelangfeldt.weatherapp.domain.FormatTemperatureUseCase
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jakelangfeldt.weatherapp.data.datasource.response.Forecast as ForecastResponse
import com.jakelangfeldt.weatherapp.data.datasource.response.Forecasts as ForecastsResponse
import com.jakelangfeldt.weatherapp.data.datasource.response.Temperature as TemperatureResponse

@HiltViewModel
class ForecastsViewModel @Inject constructor(
    private val formatTemperatureUseCase: FormatTemperatureUseCase,
    private val openWeatherMapRepository: OpenWeatherMapRepository,
) :
    ViewModel() {

    private val _forecastsState = MutableLiveData<ForecastsState>()
    val forecastsState: LiveData<ForecastsState>
        get() = _forecastsState

    fun fetchData(zip: Int) {
        viewModelScope.launch {
            fetchCoordinates(zip)
        }
    }

    private suspend fun fetchCoordinates(zip: Int) {
        val coordinatesResult =
            openWeatherMapRepository.getCoordinates(zip, BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        if (coordinatesResult is Result.Success<Coordinates> && coordinatesResult.data.lat != null && coordinatesResult.data.lon != null) {
            fetchForecasts(coordinatesResult.data.lat, coordinatesResult.data.lon, coordinatesResult.data.name)
        } else {
            // TODO
        }
    }

    private suspend fun fetchForecasts(lat: Double, lon: Double, name: String?) {
        val forecastsResult = openWeatherMapRepository.getForecasts(
            lat,
            lon,
            BuildConfig.OPEN_WEATHER_MAP_API_KEY
        )

        if (forecastsResult is Result.Success<ForecastsResponse>) {
            _forecastsState.value = forecastsResult.data.toForecastsState(name)
        } else {
            // TODO
        }
    }

    private fun ForecastsResponse.toForecastsState(name: String?) = ForecastsState(
        name = name,
        forecasts = this.daily?.map { it.toForecast() }.orEmpty(),
    )

    private fun ForecastResponse.toForecast() = Forecast(
        temperature = this.temp?.toTemperature()
    )

    private fun TemperatureResponse.toTemperature() = Temperature(
        min = formatTemperatureUseCase(this.min),
        max = formatTemperatureUseCase(this.max),
    )
}
