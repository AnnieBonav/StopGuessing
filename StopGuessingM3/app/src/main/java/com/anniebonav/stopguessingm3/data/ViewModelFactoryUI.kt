package com.anniebonav.stopguessingm3.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryUI(var context: Context): ViewModelProvider.Factory  {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UIViewModel::class.java)){
            return UIViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}