package com.ilyko.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ilyko.weatherapp.domain.model.CityWeather
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Provider
import kotlin.concurrent.thread


@Database(entities = [CityWeatherDb::class], version = WeatherDB.VERSION)
abstract class WeatherDB : RoomDatabase(), Serializable {

    //abstract val cityDao: CityDao
    abstract fun cityDao(): CityDao

    companion object {
        const val VERSION = 1
    }

/*    class OnCreateCallback @Inject constructor(
            private val database: Provider<WeatherDB>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            *//*thread {
                database.get()
                        .cityDao
                        .insertAll(CityWeatherDb.populateCities())
            }*//*
        }
    }*/
}