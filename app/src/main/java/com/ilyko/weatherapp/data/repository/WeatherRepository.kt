package com.ilyko.weatherapp.data.repository

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.ilyko.weatherapp.data.api.WeatherWebService
import com.ilyko.weatherapp.data.db.CityDao
import com.ilyko.weatherapp.data.mappers.DbMapper
import com.ilyko.weatherapp.domain.model.CityWeather
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import java.util.*
import java.util.function.Predicate


class WeatherRepository @Inject constructor(
    private val weatherWebService: WeatherWebService,
    private val cityDao: CityDao,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val dbMapper: DbMapper
) : Repository {

    override fun addNewCity(lat: Double, lon: Double): Observable<List<CityWeatherDb>> {
        return Observable.create { result ->
            weatherWebService.getCityWeatherByCoordinates(lat, lon)
                .subscribeOn(Schedulers.io())
                .map {
                    dbMapper.map(Pair(it, false))
                }
                .subscribe({
                    cityDao.insert(it)
                    result.onNext(cityDao.getAllCities().blockingFirst())
                }, {
                    result.onError(it)
                })
        }
    }

    override fun getAllCities(): Observable<List<CityWeatherDb>> {
        return getWeather()!!.toObservable()
    }

    private fun getAllCitiesApi(): Flowable<List<CityWeatherDb>>? {
        return Single.mergeDelayError(
            getCityByLocation(),
            loadCities(getListOfCurrentCities())
        ).toList()
            .map {
                it.flatten()
            }.toFlowable().onErrorResumeNext { throwable: Throwable ->
                throwable.printStackTrace()
                Flowable.empty<List<CityWeatherDb>>()
            }
    }

    private fun getListOfCurrentCities(): List<CityWeatherDb> {
        return if (cityDao.getAddedCities().blockingFirst().isEmpty()) {
            CityWeatherDb.populateCities()
        } else {
            cityDao.getAddedCities().blockingFirst()
        }
    }

    private fun loadCities(cities: List<CityWeatherDb>): Single<List<CityWeatherDb>> {
        return Single.create { result ->
            Observable
                .fromIterable(cities)
                //.flatMap { weatherWebService.getCityWeatherByQuery(it.name).observeOn(Schedulers.io()) }
                .flatMap { weatherWebService.getCityWeatherByCoordinates(it.lat, it.lon).observeOn(Schedulers.io()) }
                .map {
                    dbMapper.map(Pair(it, false))
                }
                .toList()
                .subscribe({
                    cityDao.insertAll(it)
                    result.onSuccess(it)
                }, {
                    result.onError(it)
                })
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(): Observable<Location> {
        val subject = BehaviorSubject.create<Location>()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { subject.onNext(location) }
        }
        return subject
    }

    private fun getCityByLocation(): Single<List<CityWeatherDb>> {
        return Single.create { result ->
            getLocation().observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    weatherWebService.getCityWeatherByCoordinates(it.latitude, it.longitude).observeOn(Schedulers.io())
                }
                .map {
                    dbMapper.map(Pair(it, true))
                }
                .subscribe({
                    cityDao.updateLocation(it)
                    result.onSuccess(listOf(it))
                }, {
                    result.onError(it)
                })
        }
    }

    private fun getWeather(): Single<List<CityWeatherDb>>? {
        val dbCities = cityDao.getAllCities()
        val networkCities = getAllCitiesApi()

        return Flowable.concat(networkCities, dbCities)
            .filter { it.isNotEmpty() }
            .first(Collections.emptyList())
    }

}