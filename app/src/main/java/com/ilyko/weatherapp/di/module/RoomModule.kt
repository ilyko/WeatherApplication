package com.ilyko.weatherapp.di.module

import android.content.Context
import androidx.room.Room
import com.ilyko.weatherapp.data.db.CityDao
import com.ilyko.weatherapp.data.db.WeatherDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): WeatherDB {
        return Room.databaseBuilder(context, WeatherDB::class.java, "weather.db")
                //.addCallback(callback)
                .build()
    }

    @Singleton
    @Provides
    fun providesWeatherDao(weatherDB: WeatherDB): CityDao {
        return weatherDB.cityDao()
    }



/*    @Provides
    fun providesCityRepository(weatherWebService: WeatherWebService, cityDao: CityDao): WeatherRepository {
        return WeatherRepository(weatherWebService, cityDao)
    }*/

}