package com.anniebonav.stopguessingm3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanDao

@Database(entities = [MealPlan::class], version = 1)
abstract class StopGuessingDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun ingredientDao(): IngredientDAO

    companion object {
        private var INSTANCE: StopGuessingDatabase? = null
        fun getDatabase(context: Context): StopGuessingDatabase {
            if(INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, StopGuessingDatabase::class.java, "StopGuessingDatabase").build()
                }
            }
            return INSTANCE!!
        }
    }
}