package com.anniebonav.stopguessingm3
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM mealplan")
    fun getAll(): List<MealPlan>

    @Query("SELECT * FROM mealplan WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<MealPlan>

    @Insert
    fun insertAll(vararg users: MealPlan)

    @Delete
    fun delete(user: MealPlan)
}