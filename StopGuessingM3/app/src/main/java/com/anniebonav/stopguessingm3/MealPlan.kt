package com.anniebonav.stopguessingm3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class MealPlan(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "name") val mealPlanName: String?,
    @ColumnInfo(name = "description") val mealPlanDescription: String?,
    @ColumnInfo(name = "mealsAmount") val mealsAmount: Int?,
    @ColumnInfo(name = "snacksAmount") val snacksAmount: Int?,

    //@ColumnInfo(name = "snacksAmount") val dateCreated: Date?
)
