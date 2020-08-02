package com.example.optisolassesment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java))
            return SharedViewModel(dataRepository) as T
        else
            throw IllegalArgumentException("Unknown viewmodel class")
    }

}
