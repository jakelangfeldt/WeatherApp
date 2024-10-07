package com.jakelangfeldt.weatherapp.data.repository

import com.jakelangfeldt.weatherapp.data.datasource.response.Coordinates
import com.jakelangfeldt.weatherapp.data.datasource.response.Forecasts

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

interface OpenWeatherMapRepository {

    suspend fun getCoordinates(zip: Int, appId: String): Result<Coordinates>
    suspend fun getForecasts(lat: Double, lon: Double, appId: String): Result<Forecasts>
}
