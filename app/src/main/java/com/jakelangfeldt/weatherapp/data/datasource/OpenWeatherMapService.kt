package com.jakelangfeldt.weatherapp.data.datasource

import com.jakelangfeldt.weatherapp.data.datasource.response.Coordinates
import com.jakelangfeldt.weatherapp.data.datasource.response.Forecasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {

    @GET("geo/1.0/zip")
    suspend fun getCoordinates(
        @Query("zip") zip: String,
        @Query("appid") appId: String,
    ): Response<Coordinates>

    @GET("data/3.0/onecall")
    suspend fun getForecasts(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") appId: String,
    ): Response<Forecasts>
}
