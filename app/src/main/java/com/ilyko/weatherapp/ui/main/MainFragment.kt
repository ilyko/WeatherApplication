package com.ilyko.weatherapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.ilyko.weatherapp.R
import com.ilyko.weatherapp.databinding.MainFragmentBinding
import com.ilyko.weatherapp.ui.common.BaseFragment
import com.ilyko.weatherapp.ui.map.MapsActivity
import com.ilyko.weatherapp.utils.ConnectionManager
import com.ilyko.weatherapp.utils.Constants
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment :
    BaseFragment<MainViewModel, MainFragmentBinding>(MainViewModel::class.java, R.layout.main_fragment) {

    companion object {
        private const val SEARCH_CODE_REQUEST = 1
        fun newInstance() = MainFragment()
    }

    @Inject lateinit var connectionManager: ConnectionManager

    private lateinit var adapter: CityAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = CityAdapter { weather ->
            //replaceFragment(MapFragment.newInstance(weather), R.id.container, MapFragment::class.qualifiedName)

            if(connectionManager.checkInternetConnection()) {
                startActivity(MapsActivity.newIntent(activity!!, weather))
            } else {
                Toast.makeText(activity!!, R.string.no_connection_map_hint, Toast.LENGTH_SHORT).show()
            }
        }
        rvCities.adapter = adapter
        initRecyclerView()

        binding.addCity.setOnClickListener {
            if(connectionManager.checkInternetConnection()) {
                openAutocompleteActivity()
            } else {
                Toast.makeText(activity!!, R.string.no_connection_add_city_hint, Toast.LENGTH_SHORT).show()
            }
            openAutocompleteActivity()
        }
    }

    private fun initRecyclerView() {
        viewModel.cities.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result)
        })
    }

    private fun openAutocompleteActivity() {
        if (!Places.isInitialized()) {
            Places.initialize(activity!!, Constants.GEO_API_KEY)
        }

        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        )
            .build(activity!!)
        startActivityForResult(intent, SEARCH_CODE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data)
                place.latLng?.let {
                    viewModel.addCity(it)
                }
            }
        }
    }

}

