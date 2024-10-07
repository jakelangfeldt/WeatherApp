package com.jakelangfeldt.weatherapp.data.repository

import com.jakelangfeldt.weatherapp.data.datasource.OpenWeatherMapService
import com.jakelangfeldt.weatherapp.data.datasource.response.Coordinates
import com.jakelangfeldt.weatherapp.data.datasource.response.Forecasts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val COORDINATES_UNAVAILABLE = "Coordinates is null"
private const val FORECASTS_UNAVAILABLE = "Forecasts is null"

class OpenWeatherMapRepositoryImpl @Inject constructor(private val openWeatherMapService: OpenWeatherMapService) :
    OpenWeatherMapRepository {

    override suspend fun getCoordinates(zip: Int, appId: String): Result<Coordinates> =
        withContext(Dispatchers.IO) {
            try {
                val coordinatesResponse =
                    openWeatherMapService.getCoordinates("$zip,US", appId).body()
                if (coordinatesResponse != null) {
                    Result.Success(coordinatesResponse)
                } else {
                    Result.Error(Exception(COORDINATES_UNAVAILABLE))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getForecasts(
        lat: Double,
        lon: Double,
        appId: String
    ): Result<Forecasts> = withContext(Dispatchers.IO) {
        try {
            val forecastsResponse = openWeatherMapService.getForecasts(
                lat,
                lon,
                "current,minute,hourly,alerts",
                "imperial",
                appId
            ).body()
            if (forecastsResponse != null) {
                Result.Success(forecastsResponse)
            } else {
                Result.Error(Exception(FORECASTS_UNAVAILABLE))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
