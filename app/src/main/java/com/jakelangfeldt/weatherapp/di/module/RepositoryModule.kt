package com.jakelangfeldt.weatherapp.di.module

import com.jakelangfeldt.weatherapp.data.repository.OpenWeatherMapRepository
import com.jakelangfeldt.weatherapp.data.repository.OpenWeatherMapRepositoryImpl
import com.jakelangfeldt.weatherapp.data.datasource.OpenWeatherMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(openWeatherMapService: OpenWeatherMapService): OpenWeatherMapRepository {
        return OpenWeatherMapRepositoryImpl(openWeatherMapService)
    }
}
