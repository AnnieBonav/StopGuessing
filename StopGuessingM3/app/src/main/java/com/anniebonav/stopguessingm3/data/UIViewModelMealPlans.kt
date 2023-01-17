package com.anniebonav.stopguessingm3.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan

class UIViewModelMealPlans(var context: Context): ViewModel() {
    private val db:StopGuessingDatabase = StopGuessingDatabase.getDatabase(context)
    val currentMealPlans: LiveData<List<MealPlan>> = db.mealPlanDao().getAllLiveData()

    fun insertMealPlan(mealPlan: MealPlan){
        db.mealPlanDao().insertAll(mealPlan)
    }
}