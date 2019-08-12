package com.ilyko.weatherapp.ui.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilyko.weatherapp.R
import com.ilyko.weatherapp.databinding.MapFragmentBinding
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import com.ilyko.weatherapp.ui.common.BaseFragment
import java.lang.StringBuilder


class MapFragment : BaseFragment<MapViewModel, MapFragmentBinding>(MapViewModel::class.java, R.layout.activity_maps),
    OnMapReadyCallback {
    companion object {
        private const val ARG_CITY_NAME = "city_name"

        fun newInstance(city: CityWeatherDb) = MapFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CITY_NAME, city)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private lateinit var mMap: GoogleMap


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

        val city: CityWeatherDb = arguments?.getSerializable(ARG_CITY_NAME) as CityWeatherDb
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