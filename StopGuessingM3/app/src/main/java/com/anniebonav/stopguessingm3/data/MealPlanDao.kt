package com.anniebonav.stopguessingm3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomSQLiteQuery
import androidx.room.util.DBUtil

@Dao
interface MealPlanDao {

    @Query("SELECT * FROM mealplan")
    fun getAll(): List<MealPlan>

    @Query("SELECT * FROM mealplan")
    fun getAllLiveData(): LiveData<List<MealPlan>>

    @Query("SELECT * FROM mealplan WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<MealPlan>

    @Insert
    fun insertAll(vararg users: MealPlan)

    @Delete
    fun delete(user: MealPlan)
}