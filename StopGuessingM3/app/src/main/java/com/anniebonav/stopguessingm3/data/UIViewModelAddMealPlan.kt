package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelAddMealPlan : ViewModel() {

    val currentMealPlanName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentMealPlanDescription: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentMealPlanBlueprint: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}