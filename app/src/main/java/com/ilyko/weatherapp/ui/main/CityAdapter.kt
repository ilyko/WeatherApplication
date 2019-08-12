package com.ilyko.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ilyko.weatherapp.R
import com.ilyko.weatherapp.databinding.CityItemBinding
import com.ilyko.weatherapp.domain.model.CityWeatherDb
import com.ilyko.weatherapp.ui.common.DataBoundListAdapter


class CityAdapter(
        private val OnCityClick: ((CityWeatherDb) -> Unit)?
) : DataBoundListAdapter<CityWeatherDb, CityItemBinding>(
        diffCallback = object : DiffUtil.ItemCallback<CityWeatherDb>() {
            override fun areItemsTheSame(oldItem: CityWeatherDb, newItem: CityWeatherDb): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CityWeatherDb, newItem: CityWeatherDb): Boolean {
                return oldItem == newItem
            }
        }
) {

    override fun createBinding(parent: ViewGroup): CityItemBinding {
        val binding = DataBindingUtil.inflate<CityItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.city_item,
                parent,
                false
        )
        binding.root.setOnClickListener {
            binding.cityWeather?.let {
                OnCityClick?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: CityItemBinding, item: CityWeatherDb) {
        binding.cityWeather = item
    }
}