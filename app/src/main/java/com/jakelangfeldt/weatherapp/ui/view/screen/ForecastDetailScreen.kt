package com.jakelangfeldt.weatherapp.ui.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jakelangfeldt.weatherapp.ui.viewmodel.state.Forecast

@Composable
fun ForecastDetailScreen(
    forecast: Forecast? = null,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "${forecast?.temperature?.min.orEmpty()} / ${forecast?.temperature?.max.orEmpty()}")
    }
}
