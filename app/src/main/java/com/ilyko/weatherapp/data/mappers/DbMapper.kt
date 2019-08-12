package com.ilyko.weatherapp.data.mappers

import com.ilyko.weatherapp.domain.model.CityWeather
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import javax.inject.Inject


class DbMapper
@Inject
constructor() : Mapper<CityWeatherDb, Pair<CityWeather, Boolean>> {
    override fun map(input: Pair<CityWeather, Boolean>): CityWeatherDb {
        val (cityWeather, isLocation) = input
        return CityWeatherDb(
            cityWeather.name,
            cityWeather.id,
            cityWeather.coord.lat,
            cityWeather.coord.lon,
            cityWeather.main.temp.toString(),
            isLocation
        )
    }
}