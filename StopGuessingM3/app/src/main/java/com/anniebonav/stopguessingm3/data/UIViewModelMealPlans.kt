package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelMealPlans: ViewModel() {
    val currentMealPlans: MutableLiveData<ArrayList<MealPlan>> by lazy {
        MutableLiveData<ArrayList<MealPlan>>()
    }


}