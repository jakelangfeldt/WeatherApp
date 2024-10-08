package com.jakelangfeldt.weatherapp.domain

import javax.inject.Inject
import kotlin.math.roundToInt

private const val DEGREES_SYMBOL = "\u00B0"

class FormatTemperatureUseCase @Inject constructor() {

    operator fun invoke(temperature: Double?) =
        if (temperature != null) {
            temperature.roundToInt().toString() + DEGREES_SYMBOL
        } else {
            ""
        }
}
