package com.anniebonav.stopguessingm3.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryMealPlansUI(var context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UIViewModelMealPlans::class.java)){
            return UIViewModelMealPlans(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}