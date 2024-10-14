package com.jakelangfeldt.weatherapp.ui.view.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jakelangfeldt.weatherapp.ui.theme.WeatherAppTheme
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.ForecastsState
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Temperature
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Weather

@Composable
fun ForecastsScreen(
    forecastsState: ForecastsState,
    onSubmitZipCode: (Int) -> Unit,
    onListItemClick: (Forecast) -> Unit,
    modifier: Modifier = Modifier,
) {
    var zipCodeText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        TextField(
            value = zipCodeText,
            onValueChange = { zipCodeText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Zip code") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { onSubmitZipCode(zipCodeText.toInt()) })
        )
        Text(
            text = forecastsState.location ?: "Please enter a zip code",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
        ForecastsList(forecastsState.forecasts, onListItemClick)
    }
}

@Composable
fun ForecastsList(
    forecasts: List<Forecast>,
    onItemClick: ((Forecast) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    WeatherAppSurface(modifier = modifier.animateContentSize()) {
        LazyColumn {
            itemsIndexed(forecasts) { index, item ->
                Column(modifier = Modifier.animateItem()) {
                    ForecastItem(item, onItemClick)

                    if (index < forecasts.lastIndex) {
                        HorizontalDivider()
                    }
                }
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
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent,
        onClick = { onClick?.invoke(forecast) }) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = forecast.date.orEmpty())
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = forecast.weather?.iconUrl.orEmpty(),
                    contentDescription = "Weather icon",
                    modifier = Modifier.padding(end = 40.dp),
                )
                Text(text = "${forecast.temperature?.min.orEmpty()} / ${forecast.temperature?.max.orEmpty()}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastsListPreview() {
    WeatherAppTheme {
        ForecastsList(
            listOf(
                Forecast(
                    date = "Oct 14 Today",
                    temperature = Temperature(min = "60°", max = "73°"),
                    weather = Weather(iconUrl = "https://openweathermap.org/img/wn/01d@2x.png")
                ),
                Forecast(
                    date = "Oct 15 Tomorrow",
                    temperature = Temperature(min = "62°", max = "71°"),
                    weather = Weather(iconUrl = "https://openweathermap.org/img/wn/01d@2x.png")
                ),
                Forecast(
                    date = "Oct 16 Wed",
                    temperature = Temperature(min = "63°", max = "71°"),
                    weather = Weather(iconUrl = "https://openweathermap.org/img/wn/04d@2x.png")
                ),
                Forecast(
                    date = "Oct 17 Thu",
                    temperature = Temperature(min = "63°", max = "64°"),
                    weather = Weather(iconUrl = "https://openweathermap.org/img/wn/10d@2x.png")
                ),
                Forecast(
                    date = "Oct 18 Fri",
                    temperature = Temperature(min = "61°", max = "72°"),
                    weather = Weather(iconUrl = "https://openweathermap.org/img/wn/10d@2x.png")
                ),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastItemPreview() {
    WeatherAppTheme {
        ForecastItem(
            Forecast(
                date = "Oct 14 Today",
                temperature = Temperature(min = "63°", max = "73°"),
                weather = Weather(iconUrl = "https://openweathermap.org/img/wn/01d@2x.png")
            )
        )
    }
}
