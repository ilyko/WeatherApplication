package com.ilyko.weatherapp.di.module

import com.ilyko.weatherapp.ui.main.MainFragment
import com.ilyko.weatherapp.ui.map.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun bindMapFragment(): MapFragment
}