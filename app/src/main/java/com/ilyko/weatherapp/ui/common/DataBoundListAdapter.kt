package com.ilyko.weatherapp.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundListAdapter.DataBoundViewHolder<V>>(
        AsyncDifferConfig.Builder<T>(diffCallback)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)

    class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
            RecyclerView.ViewHolder(binding.root)
}

