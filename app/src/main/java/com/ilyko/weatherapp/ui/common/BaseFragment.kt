package com.ilyko.weatherapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>, @LayoutRes val layoutRes: Int) :
    DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(mViewModelClass) }

    open lateinit var binding: DB
    fun init(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
    }

    open fun onInject() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container!!)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}