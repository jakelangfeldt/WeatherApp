package com.jakelangfeldt.weatherapp.ui.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            textAlign = TextAlign.Center
        )
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = forecast?.date.orEmpty())
            Text(text = forecast?.dayOfWeek.orEmpty())
            Text(text = "${forecast?.temperature?.min.orEmpty()} / ${forecast?.temperature?.max.orEmpty()}")
        }
        HorizontalDivider()
        Text(
            text = forecast?.summary.orEmpty(), modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
