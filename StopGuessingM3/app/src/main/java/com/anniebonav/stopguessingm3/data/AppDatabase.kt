package com.anniebonav.stopguessingm3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao

@Database(entities = [MealPlan::class, Ingredient::class, Blueprint::class], version = 1)
abstract class StopGuessingDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun ingredientDao(): IngredientDAO
    abstract fun blueprintDao(): BlueprintDAO

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