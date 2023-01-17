package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM mealplan")
    fun getMealPlans(): List<MealPlan>

    @Query("SELECT * FROM mealplan WHERE uid = :mealPlanId")
    fun getMealPlan(mealPlanId: Int): MealPlan

    @Query("SELECT * FROM mealplan")
    fun getAllLiveData(): LiveData<List<MealPlan>>

    @Query("SELECT * FROM mealplan WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<MealPlan>

    @Insert
    fun insertAll(vararg users: MealPlan)

    @Query("DELETE FROM mealplan WHERE uid = :mealPlanId")
    fun deleteMealPlan(mealPlanId: Int)

    @Delete
    fun delete(mealPlan: MealPlan)

    @Update
    fun update(vararg users: MealPlan)
}