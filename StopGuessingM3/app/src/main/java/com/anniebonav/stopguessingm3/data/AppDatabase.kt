package com.anniebonav.stopguessingm3
import androidx.room.Database
import androidx.room.RoomDatabase
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanDao

@Database(entities = arrayOf(MealPlan::class), version = 1)
abstract class MealPlanDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao
}