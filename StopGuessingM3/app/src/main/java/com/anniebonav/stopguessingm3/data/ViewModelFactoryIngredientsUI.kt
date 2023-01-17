package com.anniebonav.stopguessingm3.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryIngredientsUI(var context: Context): ViewModelProvider.Factory  {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UIViewModelIngredients::class.java)){
            return UIViewModelIngredients(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}