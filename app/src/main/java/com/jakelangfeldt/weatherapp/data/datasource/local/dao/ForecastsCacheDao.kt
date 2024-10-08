package com.jakelangfeldt.weatherapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jakelangfeldt.weatherapp.data.datasource.local.entity.ForecastsCache

@Dao
interface ForecastsCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(forecastsCache: ForecastsCache)

    @Query("SELECT * FROM forecasts_cache WHERE zip_code = :zipCode")
    suspend fun getCache(zipCode: Int): ForecastsCache?

    companion object {
        const val CACHE_INVALIDATION_TIME = 24 * 60 * 60 * 1_000
    }
}
