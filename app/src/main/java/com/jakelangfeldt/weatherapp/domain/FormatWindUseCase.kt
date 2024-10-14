package com.jakelangfeldt.weatherapp.domain

import javax.inject.Inject
import kotlin.math.roundToInt

private val directions = listOf(
    "N",
    "NNE",
    "NE",
    "ENE",
    "E",
    "ESE",
    "SE",
    "SSE",
    "S",
    "SSW",
    "SW",
    "WSW",
    "W",
    "WNW",
    "NW",
    "NNW",
)

class FormatWindUseCase @Inject constructor() {

    operator fun invoke(speed: Double?, degrees: Int?) =
        if (speed != null && degrees != null) {
            val direction = directions.getOrNull(
                (degrees.toDouble() / (360 / directions.size)).roundToInt().mod(directions.size)
            )
            "${speed.roundToInt()} mph ${direction.orEmpty()}"
        } else {
            ""
        }
}
