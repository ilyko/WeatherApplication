package com.ilyko.weatherapp.domain.model


data class CityWeather(
    val name: String,
    val id: Int,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val main: Main,
    val message: String
)

data class Main(
    var temp: Double
)

data class Coord(
    var lat: Double,
    var lon: Double
)
