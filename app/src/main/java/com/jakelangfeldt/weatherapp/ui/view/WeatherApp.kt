package com.jakelangfeldt.weatherapp.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jakelangfeldt.weatherapp.ui.view.screen.ForecastDetailScreen
import com.jakelangfeldt.weatherapp.ui.view.screen.ForecastsScreen
import com.jakelangfeldt.weatherapp.ui.viewmodel.ForecastsViewModel
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState

@Composable
fun WeatherApp(
    viewModel: ForecastsViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WeatherAppScreen.valueOf(
        backStackEntry?.destination?.route ?: WeatherAppScreen.ForecastsList.name
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            WeatherAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    viewModel.updateSelectedForecast(null)
                    navController.navigateUp()
                },
            )
        },
    ) { innerPadding ->
        val forecastsState = viewModel.forecastsState.observeAsState(initial = ForecastsState())

        LaunchedEffect(forecastsState.value.selectedForecast) {
            if (forecastsState.value.selectedForecast != null) {
                navController.navigate(WeatherAppScreen.ForecastDetail.name)
            }
        }

        NavHost(
            navController = navController,
            startDestination = WeatherAppScreen.ForecastsList.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = WeatherAppScreen.ForecastsList.name) {
                ForecastsScreen(
                    forecastsState = forecastsState.value,
                    onSubmitZipCode = { zipCode: Int -> viewModel.fetchData(zipCode) },
                    onListItemClick = { forecast: Forecast ->
                        viewModel.updateSelectedForecast(forecast)
                    })
            }
            composable(route = WeatherAppScreen.ForecastDetail.name) {
                ForecastDetailScreen(forecastsState.value.selectedForecast)
            }
        }
    }
}

enum class WeatherAppScreen(val title: String) {
    ForecastsList(title = "WeatherApp"),
    ForecastDetail(title = "Forecast"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    currentScreen: WeatherAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        }
    )
}
