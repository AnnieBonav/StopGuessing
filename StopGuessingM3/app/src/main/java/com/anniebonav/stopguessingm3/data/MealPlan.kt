package com.anniebonav.stopguessingm3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealPlan(
    @PrimaryKey(autoGenerate = true) val uid: Int?, //TODO probably can change thsi to be without the val and only the autogenerate + true
    @ColumnInfo(name = "name") val mealPlanName: String?,
    @ColumnInfo(name = "description") val mealPlanDescription: String?,
    @ColumnInfo(name = "mealsAmount") val mealsAmount: Int?,
    @ColumnInfo(name = "snacksAmount") val snacksAmount: Int?,

    //@ColumnInfo(name = "snacksAmount") val dateCreated: Date?
)
