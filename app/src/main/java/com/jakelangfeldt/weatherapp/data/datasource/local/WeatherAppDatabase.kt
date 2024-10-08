package com.jakelangfeldt.weatherapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jakelangfeldt.weatherapp.data.datasource.local.dao.ForecastsCacheDao
import com.jakelangfeldt.weatherapp.data.datasource.local.entity.ForecastsCache

@Database(entities = [ForecastsCache::class], version = 1)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun forecastsCacheDao(): ForecastsCacheDao

    companion object {
        const val DATABASE_NAME = "weather-app-database"
    }
}
