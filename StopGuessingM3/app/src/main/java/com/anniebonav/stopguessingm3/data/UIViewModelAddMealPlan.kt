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

    val currentMealsAmount: MutableLiveData<String> by lazy {
        MutableLiveData<String>() //TODO Will use them as strings to be able to
    }

    val currentSnacksAmount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}