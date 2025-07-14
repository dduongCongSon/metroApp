package org.com.metro.model

data class HourlyWeatherResponse(
    val hourly: HourlyWeatherData
)

data class HourlyWeatherData(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val wind_speed_10m: List<Double>
)
