package com.ilyko.weatherapp.ui.map

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilyko.weatherapp.R
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import java.lang.StringBuilder

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    companion object {

        private const val INTENT_CITY = "city"

        fun newIntent(context: Context, cityWeatherDb: CityWeatherDb): Intent {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra(INTENT_CITY, cityWeatherDb)
            return intent
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val city: CityWeatherDb = intent?.extras?.getSerializable(INTENT_CITY) as CityWeatherDb
        val zoomLevel = 12.0f
        val cityCoord = LatLng(city.lat, city.lon)
        val builder: StringBuilder = StringBuilder().append(city.name).append(" ").append(city.temp).append("°C")

        mMap.addMarker(
            MarkerOptions()
                .position(cityCoord)
                .title(builder.toString())
        ).showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoord, zoomLevel))
    }
}
