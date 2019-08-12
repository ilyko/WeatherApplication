package com.ilyko.weatherapp.di.module


import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ilyko.weatherapp.data.api.WeatherWebService
import com.ilyko.weatherapp.utils.ConnectionManager
import com.ilyko.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [InterceptorModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): WeatherWebService = retrofit.create(WeatherWebService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.WEATHER_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()


    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(context: Context):
            FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesConnectionManager(context: Context): ConnectionManager = ConnectionManager(context)
}

