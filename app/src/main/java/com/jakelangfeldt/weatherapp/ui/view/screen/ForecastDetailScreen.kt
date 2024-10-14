package com.jakelangfeldt.weatherapp.ui.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast

@Composable
fun ForecastDetailScreen(
    forecast: Forecast? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = forecast?.location.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
        WeatherAppSurface {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = forecast?.date.orEmpty())
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = forecast?.weather?.iconUrl.orEmpty(),
                        contentDescription = "Weather icon",
                        modifier = Modifier.padding(end = 40.dp),
                    )
                    Text(text = "${forecast?.temperature?.min.orEmpty()} / ${forecast?.temperature?.max.orEmpty()}")
                }
            }
        }
        WeatherAppSurface {
            Column {
                Text(
                    text = "Summary: ${forecast?.summary.orEmpty()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                HorizontalDivider()
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Sunrise: ${forecast?.sunrise.orEmpty()}")
                        Text(text = "Sunset: ${forecast?.sunset.orEmpty()}")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Wind: ${forecast?.wind.orEmpty()}",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Max UV: ${forecast?.uvi.orEmpty()}",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
