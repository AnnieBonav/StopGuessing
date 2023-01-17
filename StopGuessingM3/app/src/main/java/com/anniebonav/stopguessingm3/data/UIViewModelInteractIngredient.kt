package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelInteractIngredient: ViewModel() {
    val currentIngredientName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentIngredientCategory: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentIngredientAmount: MutableLiveData<String> by lazy {
        MutableLiveData<String>() //TODO Will use them as strings to be able to
    }

    val currentIngredientMeasurement: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}