package com.example.apiconnectapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val weatherApi: WeatherApiService by lazy {
    retrofit.create(WeatherApiService::class.java)
  }
}