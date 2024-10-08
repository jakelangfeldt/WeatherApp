package com.jakelangfeldt.weatherapp.data.repository

import com.jakelangfeldt.weatherapp.data.repository.model.ForecastsModel

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

interface OpenWeatherMapRepository {

    suspend fun fetchForecasts(zipCode: Int, appId: String): Result<ForecastsModel>
}
