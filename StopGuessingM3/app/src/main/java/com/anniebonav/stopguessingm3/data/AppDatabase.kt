package com.anniebonav.stopguessingm3
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanDao
import com.anniebonav.stopguessingm3.recycler.MealPlanAdapter

@Database(entities = [MealPlan::class], version = 1)
abstract class MealPlanDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao

    companion object {
        private var INSTANCE: MealPlanDatabase? = null
        fun getDatabase(context: Context): MealPlanDatabase {
            if(INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, MealPlanDatabase::class.java, "mealplans_database").build()
                }
            }
            return INSTANCE!!
        }
    }
}