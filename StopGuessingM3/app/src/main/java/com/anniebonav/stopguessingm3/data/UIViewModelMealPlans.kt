package com.anniebonav.stopguessingm3.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anniebonav.stopguessingm3.MealPlanDatabase

class UIViewModelMealPlans(var context: Context): ViewModel() {
    private val db:MealPlanDatabase = MealPlanDatabase.getDatabase(context)
    val currentMealPlans: LiveData<List<MealPlan>> = db.mealPlanDao().getAllLiveData()

    fun insertMealPlan(mealPlan: MealPlan){
        db.mealPlanDao().insertAll(mealPlan)
    }
}