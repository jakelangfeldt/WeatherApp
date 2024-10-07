package com.jakelangfeldt.weatherapp.domain

import java.util.Locale
import javax.inject.Inject

private const val DEGREES_FAHRENHEIT_SYMBOL = "\u2109"

class FormatTemperatureUseCase @Inject constructor() {

    operator fun invoke(temperature: Double?) =
        if (temperature != null) {
            String.format(Locale.getDefault(), "%.2f", temperature) + DEGREES_FAHRENHEIT_SYMBOL
        } else {
            ""
        }
}
