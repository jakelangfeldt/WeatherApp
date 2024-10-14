package com.jakelangfeldt.weatherapp.di.module

import com.jakelangfeldt.weatherapp.data.datasource.remote.OpenWeatherMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(OpenWeatherMapService.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): OpenWeatherMapService =
        retrofit.create(OpenWeatherMapService::class.java)
}
