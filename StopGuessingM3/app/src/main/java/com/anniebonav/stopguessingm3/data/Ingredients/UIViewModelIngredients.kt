package com.anniebonav.stopguessingm3.data.Ingredients

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anniebonav.stopguessingm3.StopGuessingDatabase

class UIViewModelIngredients(var context: Context): ViewModel() {
    private val db: StopGuessingDatabase = StopGuessingDatabase.getDatabase(context)
    val currentMealPlans: LiveData<List<Ingredient>> = db.ingredientDao().getAllLiveData()

    fun insertMealPlan(ingredient: Ingredient){
        db.ingredientDao().insertAll(ingredient)
    }
}