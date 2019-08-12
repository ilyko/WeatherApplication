package com.ilyko.weatherapp.di.module

import com.ilyko.weatherapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(/*includes = [AndroidInjectionModule::class]*/)
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}