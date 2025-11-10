package com.example.apiconnectapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yourname.apiconnectapp.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

  private lateinit var etCity: EditText
  private lateinit var btnSearch: Button
  private lateinit var progressBar: ProgressBar
  private lateinit var scrollView: ScrollView
  private lateinit var tvCityName: TextView
  private lateinit var tvTemperature: TextView
  private lateinit var tvDescription: TextView
  private lateinit var tvHumidity: TextView
  private lateinit var tvWind: TextView
  private lateinit var tvError: TextView

  private lateinit var networkUtils: NetworkUtils
  private val apiKey = "aa3875d65d6e215967fd71b802c0070f"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initializeViews()
    networkUtils = NetworkUtils(this)
    setupUI()
  }

  private fun initializeViews() {
    etCity = findViewById(R.id.etCity)
    btnSearch = findViewById(R.id.btnSearch)
    progressBar = findViewById(R.id.progressBar)
    scrollView = findViewById(R.id.scrollView)
    tvCityName = findViewById(R.id.tvCityName)
    tvTemperature = findViewById(R.id.tvTemperature)
    tvDescription = findViewById(R.id.tvDescription)
    tvHumidity = findViewById(R.id.tvHumidity)
    tvWind = findViewById(R.id.tvWind)
    tvError = findViewById(R.id.tvError)
  }

  private fun setupUI() {
    btnSearch.setOnClickListener {
      searchWeather()
    }

    etCity.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        searchWeather()
        true
      } else {
        false
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.M)
  private fun searchWeather() {
    val city = etCity.text.toString().trim()
    if (city.isEmpty()) {
      showError("Please enter a city name")
      return
    }

    if (!networkUtils.isNetworkAvailable()) {
      showError("No internet connection")
      return
    }

    fetchWeatherData(city)
  }

  private fun fetchWeatherData(city: String) {
    showLoading(true)

    CoroutineScope(Dispatchers.IO).launch {
      try {
        println("DEBUG: Fetching weather for: $city")
        val response = RetrofitInstance.weatherApi.getWeather(city, apiKey)

        withContext(Dispatchers.Main) {
          showLoading(false)

          println("DEBUG: Response code: ${response.code()}")

          if (response.isSuccessful) {
            val weatherData = response.body()
            if (weatherData != null) {
              displayWeatherData(weatherData)
            } else {
              showError("No weather data found")
            }
          } else {
            val errorBody = response.errorBody()?.string()
            println("DEBUG: Error response: $errorBody")

            when (response.code()) {
              401 -> showError("Invalid API key. Please wait 10-20 minutes for new keys to activate.")
              404 -> showError("City not found: $city")
              else -> showError("Error ${response.code()}: ${response.message()}")
            }
          }
        }
      } catch (e: Exception) {
        withContext(Dispatchers.Main) {
          showLoading(false)
          println("DEBUG: Exception: ${e.message}")
          showError("Network error: ${e.message}")
        }
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun displayWeatherData(weather: WeatherResponse) {
    scrollView.visibility = ScrollView.VISIBLE
    hideError()

    tvCityName.text = weather.name
    tvTemperature.text = "Temperature: ${weather.main.temp}°C"
    tvDescription.text = "Weather: ${weather.weather[0].description}"
    tvHumidity.text = "Humidity: ${weather.main.humidity}%"
    tvWind.text = "Wind Speed: ${weather.wind.speed} m/s"
  }

  private fun showLoading(show: Boolean) {
    progressBar.visibility = if (show) ProgressBar.VISIBLE else ProgressBar.GONE
    btnSearch.isEnabled = !show
  }

  private fun showError(message: String) {
    tvError.text = message
    tvError.visibility = TextView.VISIBLE
    scrollView.visibility = ScrollView.GONE
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  private fun hideError() {
    tvError.visibility = TextView.GONE
  }
}