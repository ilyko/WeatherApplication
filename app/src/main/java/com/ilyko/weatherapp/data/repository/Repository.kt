package com.ilyko.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.ilyko.weatherapp.domain.model.CityWeather
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {

    fun getAllCities() : Observable<List<CityWeatherDb>>

    fun addNewCity(lat: Double, lon: Double) : Observable<List<CityWeatherDb>>
}
