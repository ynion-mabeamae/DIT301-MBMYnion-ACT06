package com.example.apiconnectapp

import com.yourname.apiconnectapp.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
  @GET("weather")
  suspend fun getWeather(
    @Query("q") city: String,
    @Query("appid") apiKey: String,
    @Query("units") units: String = "metric"
  ): Response<WeatherResponse>
}