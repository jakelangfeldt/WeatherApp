package com.jakelangfeldt.weatherapp.di.module

import android.content.Context
import androidx.room.Room
import com.jakelangfeldt.weatherapp.data.datasource.local.WeatherAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        WeatherAppDatabase::class.java,
        WeatherAppDatabase.DATABASE_NAME,
    ).build()
}
