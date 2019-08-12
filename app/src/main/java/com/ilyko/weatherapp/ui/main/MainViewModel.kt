package com.ilyko.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ilyko.weatherapp.data.repository.WeatherRepository
import com.ilyko.weatherapp.domain.model.CityWeather
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import com.ilyko.weatherapp.utils.ConnectionManager
import com.ilyko.weatherapp.utils.SingleLiveEvent

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    init {
        loadCities()
    }

    private val _cities = MediatorLiveData<List<CityWeatherDb>>()
    val cities: LiveData<List<CityWeatherDb>> = _cities

    private fun loadCities() {
        compositeDisposable.add(
            repository.getAllCities().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _cities.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun addCity(latLng: LatLng) {
        //return connectionManager.checkInternetConnection()
        compositeDisposable.add(repository.addNewCity(latLng.latitude, latLng.longitude)
            .subscribe {
                _cities.postValue(it)
            })
    }


}
