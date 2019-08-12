package com.ilyko.weatherapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilyko.weatherapp.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_NAME)
data class CityWeatherDb(
    @ColumnInfo(name = "name")
    var name: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "lat")
    var lat: Double,
    @ColumnInfo(name = "lon")
    var lon: Double,
    @ColumnInfo(name = "temp")
    var temp: String,
    @ColumnInfo(name = "is_location")
    var isLocation: Boolean
) : Serializable {
    constructor() : this("", 0, 0.0, 0.0, "", false)

    companion object {
        fun populateCities(): List<CityWeatherDb> {
            return listOf(
                CityWeatherDb("Kharkiv", 706483, 49.99, 36.23, "", false)
            )
        }
    }
}