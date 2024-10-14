## WeatherApp - Modern Android MVVM example leveraging the latest technologies, including Hilt, Coroutines, Jetpack Compose, etc.

An Android project that fetches daily weather forecasts for the week at a provided zip code. List
items are clickable to open a detail view for the day, providing a comprehensive forecast overview.

## Setup

- Obtain an OpenWeatherMap API key with a One Call API 3.0 subscription
- Add an `api-keys.properties` file to the root directory with this `OPEN_WEATHER_MAP_API_KEY`
  property

## Features

- **MVVM (Model View ViewModel)** - architecture
- **Hilt** - dependency injection
- **Retrofit** - network
- **Room** - database (caching)
- **Coroutines** - asynchronous programming
- **Jetpack Compose** - UI
    - **LiveData** - observable/reactive data holder
    - **Material Design 3** - design system
    - **Navigation** - app navigation
- **Coil** - image loading
- **JUnit** - unit testing

## Structure

Follows Android's recommended best practices for app architecture.

- **di** - dependency injection modules
- **data** - network, database, and repository
- **domain** - utilities and use cases
- **ui** - activity, view model, and screens

## Demo

<img src="demo.gif" alt="demo" height=1200 width=540>
