package com.ilyko.weatherapp.di.component

import android.content.Context
import com.ilyko.weatherapp.App
import com.ilyko.weatherapp.data.db.WeatherDB
import com.ilyko.weatherapp.data.repository.WeatherRepository
import com.ilyko.weatherapp.di.module.*
import com.ilyko.weatherapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityModule::class,
            FragmentModule::class,
            ViewModelModule::class,
            NetworkModule::class,
            RoomModule::class
        ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    //fun cityDao(): CityDao

    fun weatherDB(): WeatherDB

    fun weatherRepository(): WeatherRepository
}