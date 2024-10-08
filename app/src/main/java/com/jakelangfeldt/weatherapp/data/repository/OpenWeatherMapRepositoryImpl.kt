package com.jakelangfeldt.weatherapp.data.repository

import com.google.gson.Gson
import com.jakelangfeldt.weatherapp.data.datasource.local.WeatherAppDatabase
import com.jakelangfeldt.weatherapp.data.datasource.local.dao.ForecastsCacheDao
import com.jakelangfeldt.weatherapp.data.datasource.local.entity.ForecastsCache
import com.jakelangfeldt.weatherapp.data.datasource.remote.OpenWeatherMapService
import com.jakelangfeldt.weatherapp.data.repository.model.Forecast
import com.jakelangfeldt.weatherapp.data.repository.model.ForecastsModel
import com.jakelangfeldt.weatherapp.data.repository.model.Temperature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.jakelangfeldt.weatherapp.data.datasource.remote.response.Forecast as ForecastResponse
import com.jakelangfeldt.weatherapp.data.datasource.remote.response.Forecasts as ForecastsResponse
import com.jakelangfeldt.weatherapp.data.datasource.remote.response.Temperature as TemperatureResponse

private const val COORDINATES_UNAVAILABLE = "Coordinates is null"
private const val FORECASTS_UNAVAILABLE = "Forecasts is null"

class OpenWeatherMapRepositoryImpl @Inject constructor(
    private val openWeatherMapService: OpenWeatherMapService,
    private val weatherAppDatabase: WeatherAppDatabase,
) : OpenWeatherMapRepository {

    override suspend fun fetchForecasts(zipCode: Int, appId: String): Result<ForecastsModel> =
        withContext(Dispatchers.IO) {
            val forecastsCache = weatherAppDatabase.forecastsCacheDao().getCache(zipCode)

            if (forecastsCache != null && System.currentTimeMillis() - forecastsCache.timestamp < ForecastsCacheDao.CACHE_INVALIDATION_TIME) {
                Result.Success(Gson().fromJson(forecastsCache.json, ForecastsModel::class.java))
            } else {
                fetchForecastsRemotely(zipCode, appId)
            }
        }

    private suspend fun fetchForecastsRemotely(
        zipCode: Int,
        appId: String
    ): Result<ForecastsModel> =
        withContext(Dispatchers.IO) {
            try {
                val coordinatesResponse =
                    openWeatherMapService.getCoordinates("$zipCode,US", appId).body()
                if (coordinatesResponse?.lat != null && coordinatesResponse.lon != null) {
                    val forecastsResponse = openWeatherMapService.getForecasts(
                        coordinatesResponse.lat,
                        coordinatesResponse.lon,
                        "current,minute,hourly,alerts",
                        "imperial",
                        appId
                    ).body()
                    if (forecastsResponse != null) {
                        val forecastsModel =
                            forecastsResponse.toForecastsModel(coordinatesResponse.name)
                        weatherAppDatabase.forecastsCacheDao().insertCache(
                            ForecastsCache(
                                zipCode,
                                Gson().toJson(forecastsModel),
                                System.currentTimeMillis()
                            )
                        )
                        Result.Success(forecastsModel)
                    } else {
                        Result.Error(Exception(FORECASTS_UNAVAILABLE))
                    }
                } else {
                    Result.Error(Exception(COORDINATES_UNAVAILABLE))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    private fun ForecastsResponse.toForecastsModel(name: String?) = ForecastsModel(
        location = name,
        timezone = timezone,
        forecasts = daily?.map { it.toForecast() }.orEmpty(),
    )

    private fun ForecastResponse.toForecast() = Forecast(
        time = dt,
        summary = summary,
        temperature = temp?.toTemperature()
    )

    private fun TemperatureResponse.toTemperature() = Temperature(
        min = min,
        max = max,
    )
}
