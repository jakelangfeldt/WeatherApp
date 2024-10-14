package com.jakelangfeldt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakelangfeldt.weatherapp.BuildConfig
import com.jakelangfeldt.weatherapp.data.repository.OpenWeatherMapRepository
import com.jakelangfeldt.weatherapp.data.repository.Result
import com.jakelangfeldt.weatherapp.data.repository.model.ForecastsModel
import com.jakelangfeldt.weatherapp.domain.DateUtilities
import com.jakelangfeldt.weatherapp.domain.DateUtilities.isInPast
import com.jakelangfeldt.weatherapp.domain.DateUtilities.toMillis
import com.jakelangfeldt.weatherapp.domain.FormatTemperatureUseCase
import com.jakelangfeldt.weatherapp.domain.FormatWindUseCase
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlin.math.roundToInt
import com.jakelangfeldt.weatherapp.data.repository.model.Forecast as ForecastModel
import com.jakelangfeldt.weatherapp.data.repository.model.Temperature as TemperatureModel
import com.jakelangfeldt.weatherapp.data.repository.model.Weather as WeatherModel

private const val NUM_FORECASTS_TO_DISPLAY = 5

@HiltViewModel
class ForecastsViewModel @Inject constructor(
    private val formatTemperatureUseCase: FormatTemperatureUseCase,
    private val formatWindUseCase: FormatWindUseCase,
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

    private fun getWeatherIconUrl(icon: String?) = if (icon != null) {
        "https://openweathermap.org/img/wn/$icon@2x.png"
    } else {
        ""
    }

    private fun ForecastsModel.toForecastsState() = ForecastsState(
        location = location,
        forecasts = forecasts?.filter {
            val timeZone = TimeZone.getTimeZone(timezone)
            !Calendar.getInstance(timeZone, Locale.getDefault())
                .apply { this.time = Date(it.time?.toMillis() ?: 0L) }.isInPast(timeZone)
        }?.take(NUM_FORECASTS_TO_DISPLAY)?.map { it.toForecast(location, timezone) }.orEmpty(),
    )

    private fun ForecastModel.toForecast(location: String?, timezone: String?) = Forecast(
        location = location,
        date = DateUtilities.getFormattedDate(time?.toMillis(), timezone),
        sunrise = DateUtilities.getFormattedTime(sunrise?.toMillis(), timezone),
        sunset = DateUtilities.getFormattedTime(sunset?.toMillis(), timezone),
        summary = summary,
        temperature = temperature?.toTemperature(),
        wind = formatWindUseCase(windSpeed, windDegrees),
        weather = weatherList?.firstOrNull()?.toWeather(),
        uvi = uvi?.roundToInt()?.toString(),
    )

    private fun TemperatureModel.toTemperature() = Temperature(
        min = formatTemperatureUseCase(min),
        max = formatTemperatureUseCase(max),
    )

    private fun WeatherModel.toWeather() = Weather(
        iconUrl = getWeatherIconUrl(icon),
    )
}
