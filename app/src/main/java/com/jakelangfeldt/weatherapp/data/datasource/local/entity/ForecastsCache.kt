package com.jakelangfeldt.weatherapp.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts_cache")
data class ForecastsCache(
    @PrimaryKey @ColumnInfo(name = "zip_code") val zipCode: Int,
    val json: String,
    val timestamp: Long,
)
