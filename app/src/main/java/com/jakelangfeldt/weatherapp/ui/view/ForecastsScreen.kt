package com.jakelangfeldt.weatherapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.jakelangfeldt.weatherapp.ui.theme.WeatherAppTheme
import com.jakelangfeldt.weatherapp.ui.viewmodel.ForecastsViewModel
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastsScreen(viewModel: ForecastsViewModel, modifier: Modifier = Modifier) {
    val forecastsState = viewModel.forecastsState.observeAsState(initial = ForecastsState())
    var zipCodeText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("WeatherApp")
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            TextField(
                value = zipCodeText,
                onValueChange = { zipCodeText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Zip code") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = { viewModel.fetchData(zipCodeText.toInt()) })
            )
            Text(text = "Location: ${forecastsState.value.location.orEmpty()}")
            HorizontalDivider()
            ForecastsList(forecastsState.value.forecasts)
        }
    }
}

@Composable
fun ForecastsList(forecasts: List<Forecast>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(forecasts) { index, item ->
            ForecastItem(item)

            if (index < forecasts.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: Forecast, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "${forecast.temperature?.min.orEmpty()} / ${forecast.temperature?.max.orEmpty()}")
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastsListPreview() {
    WeatherAppTheme {
        ForecastsList(
            listOf(
                Forecast(temperature = Temperature(min = "65.00", max = "75.00")),
                Forecast(temperature = Temperature(min = "70.00", max = "80.00")),
                Forecast(temperature = Temperature(min = "67.50", max = "77.50"))
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastItemPreview() {
    WeatherAppTheme {
        ForecastItem(Forecast(temperature = Temperature(min = "65.00", max = "75.00")))
    }
}
