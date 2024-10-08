package com.jakelangfeldt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakelangfeldt.weatherapp.BuildConfig
import com.jakelangfeldt.weatherapp.data.repository.OpenWeatherMapRepository
import com.jakelangfeldt.weatherapp.data.repository.Result
import com.jakelangfeldt.weatherapp.data.repository.model.ForecastsModel
import com.jakelangfeldt.weatherapp.domain.DateUtils
import com.jakelangfeldt.weatherapp.domain.FormatTemperatureUseCase
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jakelangfeldt.weatherapp.data.repository.model.Forecast as ForecastModel
import com.jakelangfeldt.weatherapp.data.repository.model.Temperature as TemperatureModel

@HiltViewModel
class ForecastsViewModel @Inject constructor(
    private val formatTemperatureUseCase: FormatTemperatureUseCase,
    private val openWeatherMapRepository: OpenWeatherMapRepository,
) :
    ViewModel() {

    private val _forecastsState = MutableLiveData<ForecastsState>()
    val forecastsState: LiveData<ForecastsState>
        get() = _forecastsState

    fun fetchData(zipCode: Int) {
        viewModelScope.launch {
            fetchForecasts(zipCode)
        }
    }

    fun updateSelectedForecast(forecast: Forecast?) {
        _forecastsState.value = _forecastsState.value?.copy(selectedForecast = forecast)
    }

    private suspend fun fetchForecasts(zipCode: Int) {
        val forecastsResult =
            openWeatherMapRepository.fetchForecasts(zipCode, BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        if (forecastsResult is Result.Success<ForecastsModel>) {
            _forecastsState.value = forecastsResult.data.toForecastsState()
        } else {
            _forecastsState.value = ForecastsState()
        }
    }

    private fun ForecastsModel.toForecastsState() = ForecastsState(
        location = location,
        forecasts = forecasts?.map { it.toForecast(location, timezone) }.orEmpty(),
    )

    private fun ForecastModel.toForecast(location: String?, timezone: String?) = Forecast(
        location = location,
        date = DateUtils.getFormattedDate(time, timezone),
        dayOfWeek = null,
        summary = summary,
        temperature = temperature?.toTemperature()
    )

    private fun TemperatureModel.toTemperature() = Temperature(
        min = formatTemperatureUseCase(this.min),
        max = formatTemperatureUseCase(this.max),
    )
}
