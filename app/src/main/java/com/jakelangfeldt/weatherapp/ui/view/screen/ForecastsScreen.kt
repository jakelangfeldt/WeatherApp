package com.jakelangfeldt.weatherapp.ui.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.jakelangfeldt.weatherapp.ui.theme.WeatherAppTheme
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature

@Composable
fun ForecastsScreen(
    forecastsState: ForecastsState,
    onSubmitZipCode: (Int) -> Unit,
    onListItemClick: (Forecast) -> Unit,
    modifier: Modifier = Modifier,
) {
    var zipCodeText by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            value = zipCodeText,
            onValueChange = { zipCodeText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Zip code") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { onSubmitZipCode(zipCodeText.toInt()) })
        )
        Text(text = "Location: ${forecastsState.location.orEmpty()}")
        HorizontalDivider()
        ForecastsList(forecastsState.forecasts, onListItemClick)
    }
}

@Composable
fun ForecastsList(
    forecasts: List<Forecast>,
    onItemClick: ((Forecast) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(forecasts) { index, item ->
            ForecastItem(item, onItemClick)

            if (index < forecasts.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ForecastItem(
    forecast: Forecast,
    onClick: ((Forecast) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    Surface(modifier = modifier.fillMaxWidth(), onClick = { onClick?.invoke(forecast) }) {
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
