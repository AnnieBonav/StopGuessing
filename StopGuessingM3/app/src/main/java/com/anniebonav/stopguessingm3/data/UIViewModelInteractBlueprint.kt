package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelInteractBlueprint: ViewModel() {
    val currentBlueprintName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBlueprintBreakfastUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBlueprintLunchUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBlueprintDinnerUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBlueprintMorningSnackUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBlueprintEveningSnackUnits: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}