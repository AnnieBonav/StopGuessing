package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelInteractBlueprint: ViewModel() {
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val breakfastUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val lunchUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val dinnerUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val morningSnackUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val eveningSnackUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}