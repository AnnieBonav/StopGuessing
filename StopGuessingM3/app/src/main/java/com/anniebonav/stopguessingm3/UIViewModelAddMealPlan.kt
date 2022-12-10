package com.anniebonav.stopguessingm3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelAddMealPlan : ViewModel() {
    val currentMealPlanName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}