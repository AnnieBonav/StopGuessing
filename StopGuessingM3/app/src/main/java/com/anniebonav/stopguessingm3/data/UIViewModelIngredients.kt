package com.anniebonav.stopguessingm3.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient

class UIViewModelIngredients(var context: Context): ViewModel() {
    private val db: StopGuessingDatabase = StopGuessingDatabase.getDatabase(context)
    val currentIngredients: LiveData<List<Ingredient>> = db.ingredientDao().getAllLiveData()
}