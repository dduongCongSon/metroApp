package org.com.metro.repositories.apis.weather

import org.com.metro.model.HourlyWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String = "temperature_2m,wind_speed_10m"
    ): HourlyWeatherResponse
}