package com.ilyko.weatherapp.data.api

import com.ilyko.weatherapp.utils.Constants
import com.ilyko.weatherapp.domain.model.CityWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWebService {
    @GET("weather?appid=${Constants.WEATHER_API_KEY}&units=${Constants.TEMPERATURE_UNITS}&lang=${Constants.WEATHER_API_LANG}")
    fun getCityWeatherByQuery(@Query("q") query: String): Observable<CityWeather>

    @GET("weather?appid=${Constants.WEATHER_API_KEY}&units=${Constants.TEMPERATURE_UNITS}&lang=${Constants.WEATHER_API_LANG}")
    fun getCityWeatherByCoordinates(@Query("lat") lat: Double, @Query("lon") lon: Double): Observable<CityWeather>

    @GET("group?appid=${Constants.WEATHER_API_KEY}&units=${Constants.TEMPERATURE_UNITS}&lang=${Constants.WEATHER_API_LANG}")
    fun getAllCitiesWeather(@Query("id") ids: String)
}
