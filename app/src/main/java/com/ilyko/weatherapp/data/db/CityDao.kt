package com.ilyko.weatherapp.data.db

import androidx.room.*
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import io.reactivex.Flowable

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityWeather: CityWeatherDb)

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flowable<List<CityWeatherDb>>

    @Query("SELECT * FROM cities WHERE is_location = 0")
    fun getAddedCities(): Flowable<List<CityWeatherDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<CityWeatherDb>)

    @Query("DELETE FROM cities WHERE is_location = 1")
    fun deletePreviousLocation()

    @Transaction
    fun updateLocation(cityWeather: CityWeatherDb) {
        deletePreviousLocation()
        insert(cityWeather)
    }
}